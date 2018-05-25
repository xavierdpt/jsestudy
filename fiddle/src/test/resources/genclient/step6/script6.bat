copy /Y ..\step5\client.jks .
copy /Y ..\step5\client.crq .
copy /Y ..\step5\client.crt .
copy /Y ..\step5\clientca.crt .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -import -v -alias client -keystore client.jks -storepass password -file client.crt -storetype JKS 
pause