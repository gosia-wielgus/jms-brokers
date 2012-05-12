#!/bin/sh -e

echo generating  sample CA cert:
openssl genrsa -des3 -passout pass:password -out jms-brokers-ca.key 1024
openssl req -new -passin pass:password -key jms-brokers-ca.key -subj "/C=US/ST=example/L=example/CN=JMS Brokers Sample CA" -out jms-brokers-ca.csr
openssl x509 -days 3650 -req -in jms-brokers-ca.csr -passin pass:password -signkey jms-brokers-ca.key -outform PEM -out jms-brokers-ca.crt

for store in client server ; do
    rm $store.ks
    echo generating key for $store:
    
    echo generating client and server certs:
    keytool -genkeypair -dname "cn=JMS Brokers Sample $store, ou=example, o=example, c=US" -alias jms-brokers-$store -keypass password -storepass password -keystore $store.ks -keyalg RSA

    echo genetating sign requests:
    keytool -certreq -alias jms-brokers-$store -keypass password -storepass password -keystore $store.ks -file jms-brokers-$store.csr
    
    echo signing cert:
    openssl x509 -days 3650 -req -passin pass:password -in jms-brokers-$store.csr -CA jms-brokers-ca.crt -CAkey jms-brokers-ca.key -CAcreateserial -out jms-brokers-$store.crt
    
    echo importing certs:
    keytool -import -alias jms-brokers-ca -storepass password -keystore $store.ks -file jms-brokers-ca.crt -noprompt
    keytool -import -alias jms-brokers-$store -storepass password -keystore $store.ks -file jms-brokers-$store.crt -noprompt
    rm jms-brokers-$store.crt
done
rm -f *.csr 