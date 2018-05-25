copy /Y ..\step1\client.jks .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -genkeypair -v -alias client -keystore client.jks -keypass password -storepass password -keyalg RSA -keysize 2048 -dname "CN=client, OU=Example Org, O=Example Company, L=San Francisco, ST=California, C=US"
pause