## Overview

Android EditText에 특정 문자들 차단하기 위한 클래스 **StringFilter** 

#### Functions

* public InputFilter allowAlphanumeric : 영문자와 숫자만 허용
* public InputFilter allowAlphanumericHangul : 영문자, 숫자, 한글만 허용

#### Attentions

* 천지인 키보드 처리 : 한글의 경우 천지인 키보드의 아래아 '·'를 이용하여 입력하는 경우가 있기 때문에 아래의 UniCode를 허용해줘야 한다.
 
		<string name="pattern_alphanumeric_hangul">^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2022\u2025a\u00B7\uFE55]+$</string>

#### How to use

* StringFilter.java 추가
* values/string.xml 에 Filter pattern 추가
* Filter 적용 예시 

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

If you have any questions or feedbacks, please send an email to <hyogij@gmail.com>.