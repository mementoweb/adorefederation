
# R@ Dec 2005
# Usage: createdidltape.bat -i <input> -o <tape>
#  input - directory/file name
#  tape - output xmltape

# A script to create DIDL XMLTape

tapecreate.bat --RegexIdentifier='DIDLDocumentId="([^"]+)"' -e 'diext:DIDLDocumentCreated ="([^"]+)"' --RegexRecordMatch='(<didl:DIDL.*?</didl:DIDL>)' %*

