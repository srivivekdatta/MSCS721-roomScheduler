::author:Sri Vivek Datta

@echo off

::x value declaration
set /a "x = 1"
::Creating rooms
:while1
    if %x% leq 10 (
	 echo 1
    echo room%x%
    echo 15
    echo Marist%x%
    echo HC%x%
    set /a "x = x + 1"		    
    echo room%x% added!
		goto :while1
    )
endlocal
echo 5