package com.example.raf0c.soundy;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.raf0c.soundy.constants.Constants;
import com.example.raf0c.soundy.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment mMainFragment;
    private MainFragment mainf = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mMainFragment = MainFragment.newInstance(this);

        if(savedInstanceState!=null){
            mMainFragment = getSupportFragmentManager().getFragment(savedInstanceState,"mCurrentFragment");

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mMainFragment).commit();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(Constants.KEY_USERNAME, mainf.getUsername());
        state.putString(Constants.KEY_FULL_NAME, mainf.getFullname());
        state.putString(Constants.KEY_COUNTRY, mainf.getCountry());
        state.putString(Constants.KEY_PROFPIC, mainf.getProfpic());
        getSupportFragmentManager().putFragment(state, "mCurrentFragment", mMainFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
