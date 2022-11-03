@echo off
%1 mshta vbscript:CreateObject("WScript.Shell").Run("%~s0 ::",0,FALSE)(window.close)&&exit
java -Djasypt.encryptor.password=WkZuiShuai -Dfile.encoding=utf-8  -jar C:\IdeaProject\iocoder-pro\iocoder-server\target\iocoder-server.jar >iocoder.log 2>&1 &
exit