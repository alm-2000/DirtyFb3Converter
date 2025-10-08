# DirtyFb3Converter
DirtyFb3Converter is a small utility that allows you to convert books in fb3 format to fb2 format. Conversion from fb3 to fb2 cannot be clean by definition, since the newer book format contains formatting tools that are not available in the fb2 format. But for most fiction books this does not matter, since the layout is relatively simple and the books will be converted almost without losses.

# Compilation
```
make
```
# Usage
```
java -jar DirtyFb3Converter.jar --unzipped_fb3_dir=<fb3_dir>|--fb3_file=<zip> --output=<saved_fb2>
```
Assignment of keys<br/>
--unzipped_fb3_dir - path to the unzipped book in fb3 format <br/>
--fb3_file - path to fb3 file (zipped fb3 dir)  <br/>
--output - path where to save the completed fb2
