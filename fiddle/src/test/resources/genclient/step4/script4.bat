copy /Y ..\step3\client.jks .
copy /Y ..\step3\client.crq .
"C:\Program Files\Java\jdk1.8.0_172\bin\keytool" -gencert        -v    -alias clientCA    -keystore client.jks -keypass password    -storepass password -infile client.crq -outfile client.crt -rfc -ext EKU="clientAuth" 
pause