## Overview

* Android EditText에 특정 문자들 차단하기 위한 클래스 **StringFilter** 

#### Functions

* public InputFilter allowAlphanumeric : 영문자와 숫자만 허용
* public InputFilter allowAlphanumericHangul : 영문자, 숫자, 한글만 허용
* private CharSequence filteredString(CharSequence source, int start, int end, int mode) : 특정 mode에서 허용되는 입력만 리턴하고, 그렇지 않은 경우에는 알림 toast를 띄워준다 
* private void showToast(String msg) : Toast.LENGTH_SHORT 보다 짧은 시간에 toast 를 보이기 위한 함수 

#### Attentions

* 천지인 키보드 처리 : 한글의 경우 천지인 키보드의 아래아 '·(middle dot)' 를 이용하여 모음을 만들기 때문에, 이를 허용하여야 한다. 
아래의 UniCode 들은 여러 제조사(삼성, LG, 팬택 등)의 단말기들에서 아래아 '·(middle dot)' 표현하는 값이다. 
UniCode 는 다음의 사이트 <http://www.fileformat.info/info/unicode/char/b7/index.htm> 에서 확인 할 수 있다.  
 
		<string name="pattern_alphanumeric_hangul">^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2022\u2025a\u00B7\uFE55]+$</string>

#### How to use

* Import StringFilter class :  StringFilter.java
* values/string.xml 에 String 추가
		
		<string name="pattern_alphanumeric">^[a-zA-Z0-9]+$</string>
		<string name="pattern_alphanumeric_hangul">^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2022\u2025a\u00B7\uFE55]+$</string>
		<string name="input_error_alphanum">영문자와 숫자만 허용합니다.</string>
		<string name="input_error_alphanumeric_hangul">영문자와 숫자, 한글만 허용합니다.</string>
		
* StringFilter 적용 예시 

		StringFilter stringFilter = new StringFilter(this);

		InputFilter[] allowAlphanumeric = new InputFilter[1];
		allowAlphanumeric[0] = stringFilter.allowAlphanumeric;

		// Allows only alphanumeric characters. Filters special and hangul
		// characters.
		EditText editTextAlphanumeric = (EditText) findViewById(R.id.editText01);
		editTextAlphanumeric.setFilters(allowAlphanumeric);

		InputFilter[] allowAlphanumericHangul = new InputFilter[1];
		allowAlphanumericHangul[0] = stringFilter.allowAlphanumericHangul;

		// Allows only alphanumeric and hangul characters. Filters special
		// characters.
		EditText editTextAlphanumericHangul = (EditText) findViewById(R.id.editText02);
		editTextAlphanumericHangul.setFilters(allowAlphanumericHangul);
 
#### Contact

* If you have any questions, feedback, comments or suggestions, please get in touch with me. Here is my email address: <hyogij@gmail.com>
