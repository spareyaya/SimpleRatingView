# SimpleRatingView

一个简单易用的评分组件



# 特性

- 不会误点评星
- 支持设置总分值
- 支持两种评星的样式：星星和心形
- 支持设置选中和未选中的颜色
- 支持设置星星之间的间距
- 不支持拖动设置评星



# 示例图

![srv](./images/demo.png)



# 使用方法

1. 在项目级的build.gradle中添加JitPack仓库：

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2. 添加依赖：

```groovy
dependencies {
    ...
    implementation 'com.github.spareyaya:SimpleRatingView:v1.0.5-beta'
}
```



一个完整属性的使用例子：

```xml
<com.github.spareyaya.SimpleRatingView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:starType="star"
        app:numStars="5"
        app:rating="4"
        app:starSize="48dp"
        app:starSpacing="24dp"
        app:primaryColor="#60C720"
        app:secondaryColor="#4EA0F1"
        />
```

注意：

1. 以上属性都有默认值，可以自行选择部分属性进行设置
2. 组件的大小是由padding值、评星数量（numStars）、评星大小（starSize）和评星之间的空隙（starSpacing）决定的，`android:layout_width`和`android:layout_height`不起作用。



使用回调事件：

```java
simpleRatingView1.setOnRatingChangeListener(new SimpleRatingView.OnRatingChangeListener() {
	@Override
    public void onRatingChange(int oldRating, int newRating) {
    	Toast.makeText(MainActivity.this, "oldRating:" + oldRating + " newRating:" + newRating, Toast.LENGTH_SHORT).show();
    }
});
```

