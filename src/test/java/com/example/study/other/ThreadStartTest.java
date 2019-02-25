package com.example.study.other;

import java.util.concurrent.*;

/**
 * @author lenovo
 *
 */
public class ThreadStartTest {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		System.out.print("主线程（main）0启动！");

		//实例化线程对象
		Thread_1 thread_1 = new Thread_1();
		//调用start()方法开启线程，然后会自动调用run()方法
		thread_1.start();
		
		//将实现Runnable接口对象实例化提交给Thread构造器（构造方法）
		Thread thread_2 = new Thread(new Thread_2());
		thread_2.start();

		//callable接口可以返回值，但必须用submit()提交
		ExecutorService execu = Executors.newCachedThreadPool();
		Future<String> result  = execu.submit(new TastWithResult());
		System.out.println(result.get());
		execu.shutdown();

		//线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Thread_2());
		exec.shutdown();

		//main线程
		for(int i=0;i<5;i++) {
			//等同于Thread.sleep(2000);
			TimeUnit.MILLISECONDS.sleep(2000);
			System.out.print("0");
		}
	}
}
/**
 *	继承Thread类，重写run方法
 */
class Thread_1 extends Thread {
	@Override
	public void run() {
		System.out.print("线程1启动！");
		for(int i=0;i<5;i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("1");
		}
	}
}

/**
 * 实现Runnable接口
 */
class Thread_2 implements Runnable{
	@Override
	public void run() {
		System.out.print("线程2启动！");
		for(int i=0;i<5;i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("2");
		}
	}
}

class TastWithResult implements Callable<String> {
	@Override
	public String call() throws Exception {
		return "可以返回值啦！";
	}
}