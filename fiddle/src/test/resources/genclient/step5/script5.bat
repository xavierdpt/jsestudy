copy /Y ..\step4\client.jks .
copy /Y ..\step4\client.crq .
copy /Y ..\step4\client.crt .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -export -v -alias clientCA -keystore client.jks -storepass password -file clientca.crt -rfc 
pause