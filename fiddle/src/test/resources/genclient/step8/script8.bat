copy /Y ..\step7\client.jks .
copy /Y ..\step7\client.crq .
copy /Y ..\step7\client.crt .
copy /Y ..\step7\clientca.crt .
copy /Y ..\step7\clientca.p12 .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -delete -v -alias clientCA -keystore client.jks -storepass password 
pause