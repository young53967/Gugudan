package org.androidtown.gugudan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {

    public ProgressBar prog;
    int currprog = 0; //멤버변수
    TimerTask timerTask;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prog = (ProgressBar) findViewById(R.id.progressbar);
        initProg();
        startTimerThread();
    }

    // 프로그래스bar를 초기화하는 함수
    public void initProg() {
        prog.setMax(60);// 최대값 지정
        prog.setProgress(60); // 현재값 지정
    }

    public void startTimerThread() {

        timerTask = new TimerTask() { // timerTask는 timer가 일할 내용을 기록하는 객체

            @Override
            public void run() {// 이곳에 timer가 동작할 task를 작성

                System.out.println("-----startimerrun");
                increaseBar(); // timer가 동작할 내용을 갖는 함수 호출

            }
        };

        timer = new Timer(); // timer생성
        timer.schedule(timerTask, 0, 1000); // timerTask라는 일을 갖는 timer를 0초딜레이로 1000ms마다 실행

    }// startthread

    public void increaseBar() {

        runOnUiThread( // progressBar는 ui에 해당하므로 runOnUiThread로 컨트롤해야한다

                new Runnable() { // thread구동과 마찬가지로 Runnable을 써주고

                    @Override
                    public void run() { // run을 해준다. 그러나 일반 thread처럼 .start()를 해줄 필요는 없다

                        currprog = prog.getProgress();//60
                        int maxprog = prog.getMax();//60

                        if (currprog > 0 && currprog <= maxprog)
                            currprog = currprog - 1; // 프로그래스바 1씩 증가

                        else if (currprog == 0) {

                            stopTimer();
                        }

                        prog.setProgress(currprog);
                    }

                });
    }// progress

    public void pause() {

        if (timer != null) {

            timer.cancel();
            timer.purge();
            timer = null;
        }

        else if (timer == null)
            startTimerThread();
    }

    public void stopTimer() {

        if (timerTask != null) {
            timerTask.cancel(); // 타이머task를 timer 큐에서 지워버린다
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel(); // 스케쥴task과 타이머를 취소한다.
            timer.purge(); // task큐의 모든 task를 제거한다.
            timer = null;
        }

    }// stoptimer

}
