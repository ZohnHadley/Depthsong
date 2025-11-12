@echo off
set "JAR_PATH=out\artifacts\test_taplet_os_jar\test_taplet_os.jar"
set "PI_USER=taplet"
set "PI_HOST=taplet.local"
set "PI_DIR=/home/taplet/taplet-os/"

echo Building and deploying %JAR_PATH% to %PI_USER%@%PI_HOST%:%PI_DIR%

:: Copy the JAR
scp "%JAR_PATH%" %PI_USER%@%PI_HOST%:%PI_DIR%/test_taplet_os.jar
