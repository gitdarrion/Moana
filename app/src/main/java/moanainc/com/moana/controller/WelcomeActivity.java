package moanainc.com.moana.controller;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import moanainc.com.moana.R;
import moanainc.com.moana.firebase.FirebaseInterface;
import moanainc.com.moana.model.user.AccountType;
import moanainc.com.moana.model.Model;
import moanainc.com.moana.model.ReportManager;

public class WelcomeActivity extends AppCompatActivity {

    private Button _logoutButton;
    private Button _editProfile;
    private AccountType _userAccountType;
    private WebView _webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _webView = (WebView) findViewById(R.id.webView);
        _webView.loadUrl("file:///android_asset/water.html");
        _logoutButton = (Button) findViewById(R.id.logoutButton);
        _editProfile = (Button) findViewById(R.id.editButton);
        _userAccountType = Model.getInstance().getCurrentUser().getAccount().getAccountType();

        if(_userAccountType == AccountType.MANAGER){
            findViewById(R.id.button7).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.button7).setVisibility(View.INVISIBLE);
        }

        if(_userAccountType == AccountType.ADMIN){
            findViewById(R.id.adminButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.adminButton).setVisibility(View.INVISIBLE);
        }
        TranslateAnimation animation = new TranslateAnimation(100.0f, 500.0f, 0.0f, 0.0f);
        animation.setDuration(2000);
        animation.setRepeatCount(1000000);  // animation repeat count
        animation.setRepeatMode(2);
        _webView.startAnimation(animation);
    }

    public void goToHome(View view) {
        Intent goToHome = new Intent(getBaseContext(), HomeActivity.class);
        getBaseContext().startActivity(goToHome);
    }

    public void onLogoutPressed(View view) {
        Model.getInstance().setCurrentUser(null);
        FirebaseInterface.logout();
        goToHome(null);
    }

    public void onEditProfilePressed(View view) {
        Intent goToProfile = new Intent(getBaseContext(), ProfileActivity.class);
        getBaseContext().startActivity(goToProfile);
    }

    public void onNewReport(View view) {
        final ListPopupWindow popup = new ListPopupWindow(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ReportManager.legalReports);
        popup.setAdapter(adapter);
        popup.setAnchorView(findViewById(R.id.reportButton));

        popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToCreateReport;
                switch(ReportManager.legalReports.get(position)){
                    case "Availability":
                        goToCreateReport = new Intent(getBaseContext(), AvailabilityActivity.class);
                        getBaseContext().startActivity(goToCreateReport);
                        break;
                    case "Purity":
                        if(_userAccountType == AccountType.USER || _userAccountType == AccountType.ADMIN) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Users and Admins cannot create purity reports.", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            goToCreateReport = new Intent(getBaseContext(), PurityActivity.class);
                            getBaseContext().startActivity(goToCreateReport);
                        }
                        break;
                    case "History":
                        if(_userAccountType == AccountType.USER || _userAccountType == AccountType.ADMIN) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Users and Admins cannot create history reports.", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            goToCreateReport = new Intent(getBaseContext(), HistoryActivity.class);
                            getBaseContext().startActivity(goToCreateReport);
                        }
                        break;
                    case "Source":
                        break;
                }
                popup.dismiss();
            }
        });
        popup.show();
    }

    public void onViewReports(View view) {
        final ListPopupWindow popup = new ListPopupWindow(this);

        final ArrayList<String> filterTypes = new ArrayList<>();
        filterTypes.addAll(ReportManager.legalReports);
        filterTypes.add("All");

        popup.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filterTypes));

        popup.setAnchorView(findViewById(R.id.viewButton));
        popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToViewReport;
                switch(filterTypes.get(position)){
                    case "Availability":
                        goToViewReport = new Intent(getBaseContext(), ReportListActivity.class);
                        goToViewReport.putExtra("filter", position);
                        getBaseContext().startActivity(goToViewReport);
                        break;
                    case "Purity":
                        if(_userAccountType == AccountType.USER || _userAccountType == AccountType.ADMIN) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Users and Admins cannot view purity reports.", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            goToViewReport = new Intent(getBaseContext(), ReportListActivity.class);
                            goToViewReport.putExtra("filter", position);
                            getBaseContext().startActivity(goToViewReport);
                        }
                        break;
                    case "History":
                        if(_userAccountType == AccountType.USER || _userAccountType == AccountType.ADMIN) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Users and Admins cannot view history reports.", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            goToViewReport = new Intent(getBaseContext(), ReportListActivity.class);
                            goToViewReport.putExtra("filter", position);
                            getBaseContext().startActivity(goToViewReport);
                        }
                        break;
                    case "Source":
                        break;
                    case "All":
                        if(_userAccountType == AccountType.USER || _userAccountType == AccountType.ADMIN) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Users and Admins cannot view all reports.", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            goToViewReport = new Intent(getBaseContext(), ReportListActivity.class);
                            goToViewReport.putExtra("filter", position);
                            getBaseContext().startActivity(goToViewReport);
                        }
                        break;
                }
                popup.dismiss();
            }
        });
        popup.show();
    }

    public void onHistoricalReport(View view) {
        if (_userAccountType != AccountType.MANAGER) {
            Toast toast = Toast.makeText(getApplicationContext(), "Only managers can view historical reports.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Intent goToHistoricalReport = new Intent(getBaseContext(), HistoricalReportActivity.class);
            getBaseContext().startActivity(goToHistoricalReport);
        }
    }

    public void onViewMap(View view) {
        Intent goToViewMap = new Intent(getBaseContext(), MapsActivity.class);
        getBaseContext().startActivity(goToViewMap);
    }

    public void onAdminButton(View view) {
        Intent goToViewAdmin = new Intent(getBaseContext(), AdminActivity.class);
        getBaseContext().startActivity(goToViewAdmin);
    }

    @Override
    public void onBackPressed() {
    }
}
