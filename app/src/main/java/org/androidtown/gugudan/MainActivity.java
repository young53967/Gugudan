package org.androidtown.gugudan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {

    public ProgressBar prog;
    int currprog = 0; //�������
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

    // ���α׷���bar�� �ʱ�ȭ�ϴ� �Լ�
    public void initProg() {
        prog.setMax(60);// �ִ밪 ����
        prog.setProgress(60); // ���簪 ����
    }

    public void startTimerThread() {

        timerTask = new TimerTask() { // timerTask�� timer�� ���� ������ ����ϴ� ��ü

            @Override
            public void run() {// �̰��� timer�� ������ task�� �ۼ�

                System.out.println("-----startimerrun");
                increaseBar(); // timer�� ������ ������ ���� �Լ� ȣ��

            }
        };

        timer = new Timer(); // timer����
        timer.schedule(timerTask, 0, 1000); // timerTask��� ���� ���� timer�� 0�ʵ����̷� 1000ms���� ����

    }// startthread

    public void increaseBar() {

        runOnUiThread( // progressBar�� ui�� �ش��ϹǷ� runOnUiThread�� ��Ʈ���ؾ��Ѵ�

                new Runnable() { // thread������ ���������� Runnable�� ���ְ�

                    @Override
                    public void run() { // run�� ���ش�. �׷��� �Ϲ� threadó�� .start()�� ���� �ʿ�� ����

                        currprog = prog.getProgress();//60
                        int maxprog = prog.getMax();//60

                        if (currprog > 0 && currprog <= maxprog)
                            currprog = currprog - 1; // ���α׷����� 1�� ����

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
            timerTask.cancel(); // Ÿ�̸�task�� timer ť���� ����������
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel(); // ������task�� Ÿ�̸Ӹ� ����Ѵ�.
            timer.purge(); // taskť�� ��� task�� �����Ѵ�.
            timer = null;
        }

    }// stoptimer

}
