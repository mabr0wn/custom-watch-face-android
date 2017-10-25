# CustomWatchFace
watch face project 


![alt text](https://catinean.com/content/images/2015/03/simple_watch.png)

# Project creation

I assume that you have already installed Android Studio. If not, you can grab it from here.

Start by creating a new project. Input your project name and package. Next, be sure to tick both Phone and tablet and Wear platforms on the Target Android devices screen as shown above.

![alt text](https://catinean.com/content/images/2015/03/Screen-Shot-2015-03-07-at-16-42-37.png)

Next, you won't need any activity created, so from the Add an activity windows (both mobile and wear), select Add no activity.

![alt text](https://catinean.com/content/images/2015/02/Screen-Shot-2015-02-28-at-21-14-02.png)

Lastly, click Finish and everything is set to proceed to the implementation. You will notice that two modules were created - mobile and wear.

**The following implementation details are specific only to the wear module.**

```diff
- Extending the CanvasWatchFaceService
```
In order to implement a watchface you need two core components:

[CanvasWatchFaceService](https://developer.android.com/reference/android/support/wearable/watchface/CanvasWatchFaceService.html) - the base class for watch faces which draw on a Canvas
[CanvasWatchFaceService.Engine](https://developer.android.com/reference/android/support/wearable/watchface/CanvasWatchFaceService.Engine.html) - the actual implementation of a watch face.

Start by creating a SimpleWatchFaceService class in your wear/src/main/java/yourpackage folder. This class will extend CanvasWatchFaceService and it will be the entry point of the wear application.

```java
public class SimpleWatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new SimpleEngine();
    }

    private class SimpleEngine extends CanvasWatchFaceService.Engine {

    }
}
```
