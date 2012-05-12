#!/bin/sh -e
rm -f client.ks server.ks
echo generating client and server certs:
keytool -genkeypair -dname "cn=JMS Brokers Sample Server, ou=example, o=example, c=US" -alias jms-brokers-client -keypass password -storepass password -keystore client.ks -keyalg RSA
keytool -genkeypair -dname "cn=JMS Brokers Sample Client, ou=example, o=example, c=US" -alias jms-brokers-server -keypass password -storepass password -keystore server.ks -keyalg RSA

echo genetating sign requests:
keytool -certreq -alias jms-brokers-client -keypass password -storepass password -keystore client.ks -file jms-brokers-client.csr
keytool -certreq -alias jms-brokers-server -keypass password -storepass password -keystore server.ks -file jms-brokers-server.csr

echo generating CA cert:
openssl genrsa -des3 -passout pass:password -out jms-brokers-ca.key 1024
openssl req -new -passin pass:password -key jms-brokers-ca.key -subj "/C=US/ST=Oregon/L=Portland/CN=www.madboa.com" -out jms-brokers-ca.csr

echo signing certs:
openssl x509 -req -in jms-brokers-ca.csr -passin pass:password -signkey jms-brokers-ca.key -outform PEM -out jms-brokers-ca.crt
openssl x509 -req -passin pass:password -in jms-brokers-server.csr -signkey jms-brokers-ca.key -out jms-brokers-server.crt
openssl x509 -req -passin pass:password -in jms-brokers-client.csr -signkey jms-brokers-ca.key -out jms-brokers-client.crt

echo importing certs:
keytool -keyalg RSA -import -alias jms-brokers-ca -storepass password -keystore client.ks -file jms-brokers-ca.crt -trustcacerts -noprompt
