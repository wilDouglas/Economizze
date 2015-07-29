package br.com.williamsilva.economizze.view;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.williamsilva.economizze.controller.DespesaController;
import br.com.williamsilva.economizze.controller.ReceitaController;
import br.com.williamsilva.economizze.controller.adapters.AdapterListViewMenu;
import br.com.williamsilva.economizze.controller.helpers.AlarmManagerHelper;
import br.com.williamsilva.economizze.view.fragments.DespesaFragment;
import br.com.williamsilva.economizze.view.fragments.MainFragment;
import br.com.williamsilva.economizze.view.fragments.FinancasFragments;
import br.com.williamsilva.economizze.view.fragments.NavigationDrawerFragment;
import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.view.fragments.ReceitaFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        AlarmManagerHelper.getInstance().gerarAlarmeMainService(this);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (position){

            case 0:
                fragment = new MainFragment();
                AdapterListViewMenu.itemPosition = position;
                mTitle = getString(R.string.app_name);
                break;

            case 1:
                fragment = new ReceitaFragment();
                AdapterListViewMenu.itemPosition = position;
                mTitle = getString(R.string.minhas_receitas);
                break;
            case 2:
                fragment = new DespesaFragment();
                AdapterListViewMenu.itemPosition = position;
                mTitle = getString(R.string.minhas_despesas);
                break;
            case 3:
                fragment = new FinancasFragments();
                AdapterListViewMenu.itemPosition = position;
                mTitle = getString(R.string.financas_do_mes);
                break;
            default:
                fragment = new MainFragment();
                AdapterListViewMenu.itemPosition = position;
                mTitle = getString(R.string.app_name);
                break;

        }

        this.setBackgroundActionBar(position);
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
    }

    public void setBackgroundActionBar(int posicao){ //altera a cor da action bar de acordo com a posicao seleciona pelo usuario
        switch (posicao){
            case 0:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                break;
            case 1:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde_escuro)));
                break;
            case 2:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.vermelho_claro)));
                break;
            case 3:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.azul_piscina)));
                break;
            default:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
                break;
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            /*getMenuInflater().inflate(R.menu.global, menu);*/
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.add_receita:
                startActivity(new Intent(this,ReceitaActivity.class));
                break;

            case R.id.add_despesa:
                startActivity(new Intent(this,DespesaActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
