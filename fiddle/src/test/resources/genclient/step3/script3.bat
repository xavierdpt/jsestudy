copy /Y ..\step2\client.jks .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -certreq -v -alias client -keystore client.jks -keypass password -storepass password -file client.crq 