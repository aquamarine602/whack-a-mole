package com.example.wack_a_mole;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.Random;
import java.util.ArrayList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

public class Game extends Activity implements OnClickListener {
	
	// array of integers to hold button id's
	ArrayList<Integer>button_IDs = new ArrayList<Integer>();
	
	// handler used to run timer
	protected Handler task_handler = new Handler();
	
	// variable will tell when time is up
	protected Boolean is_complete = false;
	
	// mole that is currently visible
	Button current_mole;
	
	// use current time for the game
	long start_time = System.currentTimeMillis();
	
	// number of times player has hit mole
	int whacks = 0;
	
	// default game config settings
	String player_name = "Default";
	int diff_lvl = 2;		// 1 = hard, 2 = medium, 3 = easy
	int num_moles = 8;		// between 3-8
	int duration = 20;		// up to 30 seconds

	// called when activity first created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		init_buttons();
		set_new_mole();
		set_timer(diff_lvl * 1000);
	}
	
	// method for button clicks
	@Override
	public void onClick(View v) {
		if (is_complete = true) {
			return;
		}
		if (v == current_mole) {
			whacks++;
			TextView tv = (TextView)findViewById(R.id.textView1);
			tv.setText("Number of Whacks: " + whacks);
			set_new_mole();
		}
	}


	// when game is complete
	public void game_over() {
		is_complete = true;
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText("Game Over! Score: " + whacks);
	}
	
	// chooses mole
	public void set_new_mole() {
		// random number generator
		Random rand = new Random();
		
		// find random nums
		int rand_item = rand.nextInt(num_moles);
		
		// use rand number as index
		// new mole button id is element in array
		int new_butt_id = button_IDs.get(rand_item);
		
		// makes sure there is current mole, hides it on screen
		if (current_mole != null) {
			current_mole.setVisibility(View.INVISIBLE);
		}
		
		// get new mole button using new_butt_id val
		Button new_mole = (Button)findViewById(new_butt_id);
		
		// set new mole visible
		new_mole.setVisibility(View.VISIBLE);
		
		// return new mole
		current_mole = new_mole;
	}
	
	// will retrieve all mole ids
	// array of int button ids
	// *complete as part of activity starter
	public void init_buttons() {
		// viewgroup will allow us to grab all the controls in current layout
		ViewGroup group = (ViewGroup)findViewById(R.id.LinearLayout2);
		View v;
		
		// loop through created group and find buttons
		for (int i = 0; i < group.getChildCount(); i++) {
			v = group.getChildAt(i);		// if current control is button
			
			if (v instanceof Button) {
				v.setOnClickListener(this);
				
				// if game isn't over
				if (!is_complete) {
					button_IDs.add(v.getId());		// add button id to array
					v.setVisibility(View.INVISIBLE);		// set button to visible
				}
			}
		}
	}
	
	// timer to allow us to switch current moles
	// *complete as part of activity starter
	protected void set_timer(long time) {
		// how long game is going to go
		final long elapse = time;
		
		// create runnable task -- creates time feature
		Runnable t = new Runnable() {
			public void run() {
				on_timer_task(); // change current mole on screen
				
				// if game isn't complete
				if (!is_complete) {
					// create new timer
					task_handler.postDelayed(this, elapse);
				}
			}
		};
		task_handler.postDelayed(t, elapse);
	}
	
	// change current mole when timer goes off
	// *complete as activity starter
	protected void on_timer_task() {
		long end_time = start_time + (duration * 1000);
		
		// if end time is greater than current time, game keeps going
		if (end_time > System.currentTimeMillis()) {
			set_new_mole();
		} else {
			game_over();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	

}