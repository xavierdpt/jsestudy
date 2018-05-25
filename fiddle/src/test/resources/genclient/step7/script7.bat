copy /Y ..\step6\client.jks .
copy /Y ..\step6\client.crq .
copy /Y ..\step6\client.crt .
copy /Y ..\step6\clientca.crt .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -importkeystore -v -srcalias clientCA -srckeystore client.jks          -srcstorepass password -deststorepass password -destkeystore clientca.p12 -deststoretype PKCS12
pause