del client.jks
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -genkeypair -v -alias clientCA -keystore client.jks -keypass password -storepass password -keyalg RSA -keysize 2048 -ext KeyUsage="keyCertSign" -ext BasicConstraints="ca:true" -validity 365 -dname "CN=clientCA, OU=Example Org, O=Example Company, L=San Francisco, ST=California, C=US"
pause