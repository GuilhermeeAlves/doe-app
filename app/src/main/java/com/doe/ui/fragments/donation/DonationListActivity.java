package com.doe.ui.fragments.donation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.doe.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DonationListActivity extends AppCompatActivity {

    List<String> donations;
    @BindView(R.id.lv_donations) ListView lvDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);

        ButterKnife.bind(this);

        lvDonations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Bundle bundle = new Bundle();

                //Donation tv = (Donation) this.getListAdapter().getItem(position);

                //bundle.putSerializable("donation", donation);

                Intent nextIntent = new Intent(DonationListActivity.this, DonationEditActivity.class);
                //nextIntent.putExtras(bundle);
                startActivity(nextIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //popular a listview
        donations = new ArrayList<>();
        donations.add("teste");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, donations);
        lvDonations.setAdapter(adapter);
    }
}
