copy /Y ..\step8\client.jks .
copy /Y ..\step8\client.crq .
copy /Y ..\step8\client.crt .
copy /Y ..\step8\clientca.crt .
copy /Y ..\step8\clientca.p12 .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -list -v -keystore client.jks -storepass password
pause