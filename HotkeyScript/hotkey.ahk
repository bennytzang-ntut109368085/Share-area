;^::Send {^Win}
	Return
^Left::Send {Home}
	Return
^Right::Send {End}
	Return
^Up::Send {PgUp}
	Return
^Down::Send {PgDn}
	Return

!Up::Send {Volume_Up}
	Return
!Down::Send {Volume_Down}
	Return
!WheelDown::Send {Volume_Down}
	Return
	
^Left::
	^#Left
	Return	
^Right::
	^#Right
	Return

^=::
	^w
	Return
!\::
	!tab
	Return

!\::
	^tab
	Return

^\::
	^tab
	Return

^]::
	^+tab
	Return

^1::
	^+tab
	Return
	
F1::
	!Left
	Return
F2::
	!Right
	Return

;; C:\Windows should change to %A_WinDir% for better compatibility.

;; w=window 視窗剪取
!+1::
  run, "C:\Windows\system32\SnippingTool.exe"
  Sleep, 500
  WinActivate, "Snipping Tool"
  send, !m
  send, w
  Return
  
  ;; s=screen 全螢幕剪取
^4::
  run, "C:\Windows\system32\SnippingTool.exe"
  Sleep, 500
  WinActivate, "Snipping Tool"
  send, !m
  send, s
  Return

;; r=rectangle 矩形剪取
^3::
  run, "%A_WinDir%\system32\SnippingTool.exe"
  Sleep, 500
  WinActivate, "Snipping Tool"
  send, !m
  send, r
  Return


  

;功能表
#n::run Notepad++ ;#=win
#w::run winWord
#=::
	Run calc.exe
	Return

^y::
	Run www.youtube.com
	Return
^m::
	Run www.gmail.com
	Return
	
^g::
	Run www.google.com
	Return
;熱字串
::be'::bennytzang
::be0'::bennytzang0424
::@g.::@gmail.com
::@nt.::@ntut.edu.tw


;; Scripts for obsidian.md
;; http://jdev.tw/blog
;; v0.1 2020/07/02 jerry
#SingleInstance Force

:O*:d+0::
  ;; 0409 language code for USA
  FormatTime, vToday,L0x0409, yyyy-MM-dd_ddd
  send [[%vToday%]]
  return
  
:O*:d+1::
  sendDate(1)
  return

:O*:d+2::
  sendDate(2)
  return

:O*:d-1::
  sendDate(-1)
  return

:O*:d-2::
  sendDate(-2)
  return

; 由Obsidian網站找到的腳本，我做了點小修改，以產生USA格式
; Date picker
defaultdate := %A_YYYY%%A_MM%%A_DD%

^CapsLock::
  Gui, Add, Text, x10 y15 w60 h20, StartDate:
  Gui, Add, DateTime, x+10 y10 wp20 h20 vStartDate Choose%defaultdate% Section,

  Gui, Add, Text, x10 y55 w70 h20 , StartTime:
  Gui, Add, DateTime, x+10 y50 wp hp vStartTime Choose%defaultdate%000000 1, HH:mm ;time

  Gui, Add, Button, x10 y90 w60 h20 Default, &OK
  Gui, Show, w220 h130, AHK-Scheduler
  Return


ButtonOK:
  Gui, submit, Hide
  defaultdate = %StartDate%
  FormatTime, StartDate, %StartDate% L0x0409, yyyy-MM-dd_ddd

  Send [[%StartDate%]]
  Gui, Destroy
  return

GuiClose:
  Gui Destroy
  return

GuiEscape:
  Gui Destroy
  return

sendDate(iDay) {
  ;; 0409 language code for USA
  iDate := A_Now
  ;; iToday += 1, Days
  EnvAdd, iDate, %iDay%, Days
  FormatTime, iDate,%iDate% L0x0409, yyyy-MM-dd_ddd
  send [[%iDate%]]
  return iDate
}