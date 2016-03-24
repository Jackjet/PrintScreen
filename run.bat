@echo off
set /p var=please choose to run client or server:
echo %var% is running
echo %var%/%var%.bat
::cd %var%
::start %var%.bat
start jre7/bin/java.exe -jar %var%/%var%.jar
::pause