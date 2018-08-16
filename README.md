# swipeable-button
<img src="images/swipe_new.gif" width="240" height="420" />

## Who we are
Need iOS and Android apps, MVP development or prototyping? Contact us via info@stfalcon.com. We develop software since 2009, and we're known experts in this field. Check out our [portfolio](https://stfalcon.com/en/portfolio) and see more libraries from [stfalcon-studio](https://stfalcon-studio.github.io/).

## Download
Download via Gradle:
```implement 'com.github.stfalcon:'```

## Usage
Just put this code into your layout:
```
<com.stfalcon.customswipebutton.CustomSwipeButton
   android:id="@+id/customSwipeButton"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"/>
```

You can use many attributes for more flexibility and convenience of use. Here's the full list:
- isChecked - Initial state. Default state is unchecked.
- isEnabled - Is button enabled. Boolean. True by default
- checkedText - Text for checked state
- uncheckedText - Text for unchecked state
- textSize - Text size
- thresholdStart - Value in range [0..1] indicates how much user need to swipe to change status from unchecked to checked. 0.5 by default
- thresholdEnd - Value in range [0..1] indicates how much user need to swipe to change status from checked to unchecked. 0.5 by default
- textColorChecked - Text color when button is checked
- textColorUnchecked - Text color when button is unchecked
- checkedBackground - Button background for checked state.
- uncheckedBackground - Button background for unchecked state.
- checkedToggleBackground - Toggle background when button is checked.
- uncheckedToggleBackground - Toggle background when button is unchecked.
- checkedIcon - Toggle icon when button is checked
- uncheckedIcon - Toggle icon when button is unchecked

For example:
```
<com.stfalcon.customswipebutton.CustomSwipeButton
   android:id="@+id/customSwipeButton"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:layout_marginTop="16dp"
   android:layout_marginLeft="@dimen/default_margin"
   android:layout_marginRight="@dimen/default_margin"
   app:isClickToSwipeEnable="false"
   app:checkedText="@string/checked_state_text"
   app:uncheckedText="@string/unchecked_state_text"
   app:checkedTextColor="@color/checkedTextColor"
   app:uncheckedTextColor="@color/uncheckedTextColor"
   app:swipeProgressToFinish="0.3"
   app:swipeProgressToStart="0.7"
   app:checkedIcon="@drawable/ic_done_black"
   app:uncheckedIcon="@drawable/ic_pause_black"
   app:checkedToggleBackground="@drawable/shape_sample_checked_toggle"
   app:uncheckedToggleBackground="@drawable/shape_sample_unchecked_toggle"
   app:checkedBackground="@drawable/shape_sample_scrolling_view_checked"
   app:uncheckedBackground="@drawable/shape_sample_scrolling_view_unchecked"
   app:textSize="@dimen/custom_text_size"
   app:layout_constraintTop_toBottomOf="@+id/dividerContainer"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintEnd_toEndOf="parent"/>
```
Also set them programmatically
```
customSwipeButton.isChecked = true
customSwipeButton.isEnable = true
customSwipeButton.checkedText = "Checked text"
customSwipeButton.uncheckedText = "Unchecked text"
customSwipeButton.textSize = resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
customSwipeButton.swipeProgressToFinish = 0.1
customSwipeButton.swipeProgressToStart = 0.3
customSwipeButton.checkedTextColor = ContextCompat.getColor(this,R.color.checkedTextColor)
customSwipeButton.uncheckedTextColor = ContextCompat.getColor(this,R.color.uncheckedTextColor)
customSwipeButton.checkedBackground = ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_checked)
customSwipeButton.uncheckedBackground = ContextCompat.getDrawable(this, R.drawable.shape_sample_scrolling_view_unchecked)
customSwipeButton.checkedToggleBackground = ContextCompat.getDrawable(this, R.drawable.shape_sample_checked_toggle)
customSwipeButton.uncheckedToggleBackground = ContextCompat.getDrawable(this, R.drawable.shape_sample_unchecked_toggle)
customSwipeButton.checkedIcon = ContextCompat.getDrawable(this, R.drawable.ic_done_black)
customSwipeButton.uncheckedIcon = ContextCompat.getDrawable(this, R.drawable.ic_pause_black)
```
If you want to change the state programmatically with animation you should use `setSwipeButtonState` method.
```
animateBtn.setOnClickListener {
   swipeButton.setSwipeButtonState(!customSwipeButton.isChecked)
}
```
If you want to get event from swipable-button you need to add next listeners:
 - `onSwipedListener`
 - `onSwipedOnListener`
 - `onSwipedOffListener`

Let's take look a small sample:
```
customSwipeButton.onSwipedOnListener = {
   Log.d(TAG, "onSwipedOn")
}
customSwipeButton.onSwipedOffListener = {
   Log.d(TAG, "onSwipedOff")
}
```
## License
```
Copyright 2018 stfalcon.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
