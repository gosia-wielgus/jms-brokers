#!/bin/sh -e

echo generating CA cert:
openssl genrsa -des3 -passout pass:password -out jms-brokers-ca.key 1024
openssl req -new -passin pass:password -key jms-brokers-ca.key -subj "/C=US/ST=example/L=example/CN=JMS Brokers Sample CA" -out jms-brokers-ca.csr
openssl x509 -req -in jms-brokers-ca.csr -passin pass:password -signkey jms-brokers-ca.key -outform PEM -out jms-brokers-ca.crt

for store in client server ; do
    echo generating key for $store:
    rm -f $store.ks
    echo generating client and server certs:
    keytool -genkeypair -dname "cn=JMS Brokers Sample $store, ou=example, o=example, c=US" -alias jms-brokers-$store -keypass password -storepass password -keystore $store.ks -keyalg RSA

    echo genetating sign requests:
    keytool -certreq -alias jms-brokers-$store -keypass password -storepass password -keystore $store.ks -file jms-brokers-$store.csr

    echo signing certs:
    openssl x509 -req -passin pass:password -in jms-brokers-$store.csr -signkey jms-brokers-ca.key -out jms-brokers-$store.crt

    echo importing certs:
    keytool -keyalg RSA -import -alias jms-brokers-ca -storepass password -keystore $store.ks -file jms-brokers-ca.crt -trustcacerts -noprompt
    keytool -keyalg RSA -import -alias jms-brokers-$store-byca -storepass password -keystore $store.ks -file jms-brokers-$store.crt -trustcacerts -noprompt
done
