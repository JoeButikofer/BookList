@echo off

SET inkscape="C:\Program Files (x86)\Inkscape\inkscape.exe"

%inkscape% -z ic_book_open.svg -e ../bookList\src\main\res\drawable-mdpi\ic_book_open.png -w 32 -h 32
%inkscape% -z ic_book_open.svg -e ../bookList\src\main\res\drawable-hdpi\ic_book_open.png -w 48 -h 48
%inkscape% -z ic_book_open.svg -e ../bookList\src\main\res\drawable-xhdpi\ic_book_open.png -w 64 -h 64
%inkscape% -z ic_book_open.svg -e ../bookList\src\main\res\drawable-xxhdpi\ic_book_open.png -w 96 -h 96

%inkscape% -z ic_glasses.svg -e ../bookList\src\main\res\drawable-mdpi\ic_glasses.png -w 32 -h 32
%inkscape% -z ic_glasses.svg -e ../bookList\src\main\res\drawable-hdpi\ic_glasses.png -w 48 -h 48
%inkscape% -z ic_glasses.svg -e ../bookList\src\main\res\drawable-xhdpi\ic_glasses.png -w 64 -h 64
%inkscape% -z ic_glasses.svg -e ../bookList\src\main\res\drawable-xxhdpi\ic_glasses.png -w 96 -h 96

%inkscape% -z ic_star.svg -e ../bookList\src\main\res\drawable-mdpi\ic_star.png -w 32 -h 32
%inkscape% -z ic_star.svg -e ../bookList\src\main\res\drawable-hdpi\ic_star.png -w 48 -h 48
%inkscape% -z ic_star.svg -e ../bookList\src\main\res\drawable-xhdpi\ic_star.png -w 64 -h 64
%inkscape% -z ic_star.svg -e ../bookList\src\main\res\drawable-xxhdpi\ic_star.png -w 96 -h 96

%inkscape% -z ic_tray.svg -e ../bookList\src\main\res\drawable-mdpi\ic_tray.png -w 32 -h 32
%inkscape% -z ic_tray.svg -e ../bookList\src\main\res\drawable-hdpi\ic_tray.png -w 48 -h 48
%inkscape% -z ic_tray.svg -e ../bookList\src\main\res\drawable-xhdpi\ic_tray.png -w 64 -h 64
%inkscape% -z ic_tray.svg -e ../bookList\src\main\res\drawable-xxhdpi\ic_tray.png -w 96 -h 96


pause
