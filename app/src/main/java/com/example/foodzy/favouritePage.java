package com.example.foodzy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class favouritePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbarAppbar;

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    favouriteAdapter favouriteAdapter;
    DatabaseReference ref1;
    ArrayList<favouriteModelClass> favarray = new ArrayList<>();
    private Map<String,String> reciepes = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbarAppbar = findViewById(R.id.idToolbarAppBarFav);

        setSupportActionBar(toolbarAppbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbarAppbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.idNavMenu) {
                    Intent intent = new Intent(favouritePage.this, StaticMenu.class);
                    startActivity(intent);
                } else if (id == R.id.idNavCart) {
                    Intent intent1 = new Intent(favouritePage.this, cart_details.class);
                    startActivity(intent1);
                }else if(id == R.id.idLodging){
                    Intent intent3 = new Intent(favouritePage.this, LodgingActivity.class);
                    startActivity(intent3);
                }
                else if (id==R.id.idNavFavorites){
                    Intent intent3 = new Intent(favouritePage.this,favouritePage.class);
                    startActivity(intent3);
                }
                else if (id == R.id.idNavSignOut) {
                    SharedPreferences sharedPreferences = getSharedPreferences(logInPage.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("hasLoggedIn", false);
                    editor.commit();

                    Intent intent2 = new Intent(favouritePage.this, logInPage.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(favouritePage.this, "No Correct Input", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


        reciepes.put("chicken shashlic","chicken_shashlic");
        reciepes.put("malai kofta","malai_kofta");
        reciepes.put("palak paneer","palak_paneer");
        reciepes.put("saag aloo","saag_aloo");
        reciepes.put("paneer jalfrezi","paneer_jalfrezi");
        reciepes.put("paneer vindaloo","paneer_vindaloo");
        reciepes.put("vegetable biryani","vegetable_biriyani");
        reciepes.put("tandoori chicken","tandoori_chicken");
        reciepes.put("lamb meatballs","lamb_meatballs");
        reciepes.put("paneer tikka masala","paneer_tikka_masala");
        reciepes.put("palak chicken","palak_chicken");
        reciepes.put("chicken curry","chicken_curry");
        reciepes.put("crescents","appetizers");
        reciepes.put("canapes","canapes");
        reciepes.put("stuffed samosa","stuffed_samosa");
        reciepes.put("pita chips","pita_chips");
        reciepes.put("chilli paneer","chilli_paneer");
        reciepes.put("medu vada","medu_vada");
        reciepes.put("stuffed mushroom","stuffed_mushroom");

        reciepes.put("antipasto platter","antipasto_platter");
        reciepes.put("chicken and spinach dumpling","chicken_dumpling");
        reciepes.put("crispy bacconcini","crispy_bocconcini");
        reciepes.put("chilli prawns","chilli_prawns");
        reciepes.put("bacon and cheese croquettes","bacon_and_cheese_croquettes");
        reciepes.put("bruschetta","bruschetta");
        reciepes.put("steamed dumplings","steamed_dumplings");

        reciepes.put("chocolate molten cake","molten_cake");
        reciepes.put("chocolate truffle","chocolate_truffle");
        reciepes.put("ube cheesecake","ube_cheesecake");
        reciepes.put("tiktok brownies","allrecipes_brownies");
        reciepes.put("cinnamon roll dump cake","cinnamon_cake");
        reciepes.put("marbled chocolate pumpkin pie","pumpkin_pie");

        reciepes.put("pink gin","pink_gin");
        reciepes.put("lemon fizz","lemon_fizz");
        reciepes.put("sake fizz cocktail","sake_fizz_cocktail");
        reciepes.put("refreshing vanilla strawberry iced tea","vanilla_strawbeyy_iced_tea");
        reciepes.put("peach smoothie","peach_smoothie");
        reciepes.put("rhubarb bellini smoothie","raspberry_smoothie");
        reciepes.put("turquoise tonic","turquoise_tonic");

        initData();
        initRecycleView();

    }



    public void initData(){
        ref1 = FirebaseDatabase.getInstance().getReference("FAVOURITES");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String k = ds.getKey().toString();
                    favarray.add(new favouriteModelClass(getResources().getIdentifier(reciepes.get(k.toLowerCase()),"drawable",getPackageName()),k,ds.getValue().toString()));
                }
                favouriteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void initRecycleView(){
        recyclerView = findViewById(R.id.fav_recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        favouriteAdapter = new favouriteAdapter(getApplicationContext(),favarray);
        recyclerView.setAdapter(favouriteAdapter);
        favouriteAdapter.notifyDataSetChanged();
    }


}