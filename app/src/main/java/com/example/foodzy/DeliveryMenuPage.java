package com.example.foodzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryMenuPage extends AppCompatActivity implements DeliveryCategoryOptionAdaptor.CategoryClickInterface {
    private RecyclerView recyclerViewMenuMainCourse,recyclerViewMenuAppetizers,
            recyclerViewMenuEntree, recyclerViewMenuDesert,
            recyclerViewMenuBeverages, recyclerViewCategory;
    private GridLayoutManager layoutManagerMenuMainCourse, layoutManagerMenuAppetizers,
            layoutManagerMenuEntree, layoutManagerMenuDesert,
            layoutManagerMenuBeverages;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbarAppbar;
    private LinearLayoutManager layoutManagerCategory;
    private List<DeliveryMenuOptionsModal> userListMenuMainCourse, userListMenuAppetizers,
            userListMenuEntree, userListMenuDesert, userListMenuBeverages;
    private List<DeliveryCategoryOptionModal> userListCategory ;
    private DeliveryMenuOptionsAdaptor adaptorMenuMainCourse, adaptorMenuAppetizers, adaptorMenuEntree,
            adaptorMenuDesert, adaptorMenuBeverages;
    DeliveryCategoryOptionAdaptor adaptorCategory;
    FloatingActionButton f1;

    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_menu_page);
        title = findViewById(R.id.idTVFoodtype);
        f1 = findViewById(R.id.floatingActionButton);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbarAppbar = findViewById(R.id.idToolbarAppBar);

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
                    Intent intent = new Intent(DeliveryMenuPage.this, StaticMenu.class);
                    startActivity(intent);
                } else if (id == R.id.idNavCart) {
                    Intent intent1 = new Intent(DeliveryMenuPage.this, cart_details.class);
                    startActivity(intent1);
                }else if(id == R.id.idLodging){
                    Intent intent3 = new Intent(DeliveryMenuPage.this, LodgingActivity.class);
                    startActivity(intent3);
                } else if (id == R.id.idNavSignOut) {
                    SharedPreferences sharedPreferences = getSharedPreferences(logInPage.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("hasLoggedIn", false);
                    editor.commit();

                    Intent intent2 = new Intent(DeliveryMenuPage.this, logInPage.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(DeliveryMenuPage.this, "No Correct Input", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        initDataCategory();
        initRecyclerViewCategory();

        initDataMenuMainCourse();
        initRecyclerViewMenuMainCourse();

        initDataMenuAppetizers();
        initRecyclerViewMenuAppetizers();

        initDataMenuEntree();
        initRecyclerViewMenuEntree();

        initDataMenuDesert();
        initRecyclerViewMenuDesert();

        initDataMenuBeverages();
        initRecyclerViewMenuBeverages();

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryMenuPage.this,Deliverycart_details.class);
                startActivity(intent);
            }
        });
    }


    private void initDataCategory() {
        userListCategory = new ArrayList<>();
        userListCategory.add(new DeliveryCategoryOptionModal(R.drawable.main_course, "Main Course"));
        userListCategory.add(new DeliveryCategoryOptionModal(R.drawable.appetizers, "Appetizers"));
        userListCategory.add(new DeliveryCategoryOptionModal(R.drawable.entres, "Entrées"));
        userListCategory.add(new DeliveryCategoryOptionModal(R.drawable.dessets,"Desserts"));
        userListCategory.add(new DeliveryCategoryOptionModal(R.drawable.beverages, "Beverages"));

    }
    private void initRecyclerViewCategory() {
        recyclerViewCategory = findViewById(R.id.typeFood);
        layoutManagerCategory = new LinearLayoutManager(this);

        adaptorCategory = new DeliveryCategoryOptionAdaptor(userListCategory, this, this::onCategoryClick);
        recyclerViewCategory.setAdapter(adaptorCategory);
        adaptorCategory.notifyDataSetChanged();
    }

    private void initDataMenuMainCourse(){
        title.setText("Main Course");
        userListMenuMainCourse = new ArrayList<>();
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.chicken_shashlic, "Chicken Shashlic", 1));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.malai_kofta, "Malai Kofta", 1));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.palak_paneer, "Palak Paneer", 1));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.saag_aloo, "Saag Aloo", 1));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.paneer_jalfrezi, "Paneer Jalfrezi", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.paneer_vindaloo, "Paneer Vindaloo", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.vegetable_biriyani, "Vegetable Biryani", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.tandoori_chicken, "Tandoori Chicken", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.lamb_meatballs, "Lamb Meatballs", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.paneer_tikka_masala, "Paneer Tikka Masala", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.palak_chicken, "Palak Chicken", 100));
        userListMenuMainCourse.add(new DeliveryMenuOptionsModal(R.drawable.chicken_curry, "Chicken Curry", 100));
    }
    private void initRecyclerViewMenuMainCourse(){
        recyclerViewMenuMainCourse = findViewById(R.id.foodPlate);
        layoutManagerMenuMainCourse = new GridLayoutManager(DeliveryMenuPage.this, 2);

        adaptorMenuMainCourse = new DeliveryMenuOptionsAdaptor(userListMenuMainCourse, getApplicationContext());
        recyclerViewMenuMainCourse.setAdapter(adaptorMenuMainCourse);
        adaptorMenuMainCourse.notifyDataSetChanged();
    }

    private void initDataMenuAppetizers() {
        title.setText("Appetizers");
        userListMenuAppetizers = new ArrayList<>();
        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.appetizers, "Crescents", 100));
        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.canapes, "Canapes", 1));
        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.stuffed_samosa, "Stuffed Samosa", 100));
        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.pita_chips, "Pita Chips", 100));

        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.chilli_paneer, "Chilli Paneer", 1));
        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.medu_vada, "Medu Vada", 100));
        userListMenuAppetizers.add(new DeliveryMenuOptionsModal(R.drawable.stuffed_mashrooms, "Stuffed Mushroom", 100));
    }
    private void initRecyclerViewMenuAppetizers() {
        recyclerViewMenuAppetizers = findViewById(R.id.foodPlateAppetizers);
        layoutManagerMenuAppetizers = new GridLayoutManager(DeliveryMenuPage.this, 2);

        adaptorMenuAppetizers = new DeliveryMenuOptionsAdaptor(userListMenuAppetizers, getApplicationContext());
        recyclerViewMenuAppetizers.setAdapter(adaptorMenuAppetizers);
        adaptorMenuAppetizers.notifyDataSetChanged();
    }

    private void initDataMenuEntree() {
        title.setText("Entrées");
        userListMenuEntree = new ArrayList<>();
        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.antipasto_platter, "Antipasto Platter", 100));
        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.chicken_dumpling, "Chicken and Spinach Dumpling", 100));
        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.crispy_bocconcini, "Crispy Bacconcini", 1));
        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.chilli_prawns, "Chilli Prawns", 100));

        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.bacon_and_cheese_croquettes, "Bacon and Cheese Croquettes", 100));
        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.bruschetta, "Bruschetta", 1));
        userListMenuEntree.add(new DeliveryMenuOptionsModal(R.drawable.steamed_dumplings, "Steamed Dumplings", 100));


    }
    private void initRecyclerViewMenuEntree() {
        recyclerViewMenuEntree = findViewById(R.id.foodPlateEntree);
        layoutManagerMenuEntree = new GridLayoutManager(DeliveryMenuPage.this, 2);

        adaptorMenuEntree = new DeliveryMenuOptionsAdaptor(userListMenuEntree, getApplicationContext());
        recyclerViewMenuEntree.setAdapter(adaptorMenuEntree);
        adaptorMenuEntree.notifyDataSetChanged();
    }

    private void initDataMenuDesert() {
        title.setText("Desert");
        userListMenuDesert = new ArrayList<>();
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.molten_cake, "Chocolate molten     Cake", 100));
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.chocolate_truffle, "Chocolate truffle ", 1));
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.ube_cheesecake, "Ube Cheesecake", 100));
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.allrecipes_brownies, "TikTok Brownies", 100));
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.cinnamon_cake, "Cinnamon Roll Dump Cake", 1));
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.pumpkin_pie, "Marbled Chocolate Pumpkin Pie", 100));
        userListMenuDesert.add(new DeliveryMenuOptionsModal(R.drawable.pecan_pie_cheesecake, "Marbled Chocolate Pumpkin Pie", 100));

    }
    private void initRecyclerViewMenuDesert() {
        recyclerViewMenuDesert = findViewById(R.id.foodPlateDesert);
        layoutManagerMenuDesert = new GridLayoutManager(DeliveryMenuPage.this, 2);

        adaptorMenuDesert = new DeliveryMenuOptionsAdaptor(userListMenuDesert, getApplicationContext());
        recyclerViewMenuDesert.setAdapter(adaptorMenuDesert);
        adaptorMenuDesert.notifyDataSetChanged();
    }


    private void initDataMenuBeverages() {
        title.setText("Beverages");
        userListMenuBeverages = new ArrayList<>();
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.pink_gin, "Pink Gin", 1));
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.lemon_fizz, "Lemon Fizz", 100));
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.sake_fizz_cocktail, "Sake Fizz Cocktail", 100));
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.vanilla_strawbeyy_iced_tea, "Refreshing Vanilla Strawberry Iced Tea", 100));
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.peach_smoothie, "Peach Smoothie", 1));
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.raspberry_smoothie, "Rhubarb Bellini Smoothie", 100));
        userListMenuBeverages.add(new DeliveryMenuOptionsModal(R.drawable.turquoise_tonic, " Turquoise Tonic", 100));
    }
    private void initRecyclerViewMenuBeverages() {
        recyclerViewMenuBeverages = findViewById(R.id.foodPlateBeverages);
        layoutManagerMenuBeverages = new GridLayoutManager(DeliveryMenuPage.this, 2);

        adaptorMenuBeverages = new DeliveryMenuOptionsAdaptor(userListMenuBeverages, getApplicationContext());
        recyclerViewMenuBeverages.setAdapter(adaptorMenuBeverages);
        adaptorMenuBeverages.notifyDataSetChanged();
    }


    @Override
    public void onCategoryClick(int position) {
        String category = userListCategory.get(position).getText();

        setRecyclerView(category);
    }

    private void setRecyclerView(String category) {
        if (category.equals("Main Course") ){
            recyclerViewMenuMainCourse.setVisibility(View.VISIBLE);recyclerViewMenuAppetizers.setVisibility(View.GONE);recyclerViewMenuEntree.setVisibility(View.GONE);
            recyclerViewMenuDesert.setVisibility(View.GONE);recyclerViewMenuBeverages.setVisibility(View.GONE);

            initDataMenuMainCourse();
            initRecyclerViewMenuMainCourse();
        }

        else if(category.equals("Appetizers")) {
            recyclerViewMenuMainCourse.setVisibility(View.GONE);recyclerViewMenuAppetizers.setVisibility(View.VISIBLE);recyclerViewMenuEntree.setVisibility(View.GONE);
            recyclerViewMenuDesert.setVisibility(View.GONE);recyclerViewMenuBeverages.setVisibility(View.GONE);
            initDataMenuAppetizers();
            initRecyclerViewMenuAppetizers();
        }

        else if(category.equals("Entrées")) {
            recyclerViewMenuMainCourse.setVisibility(View.GONE);recyclerViewMenuAppetizers.setVisibility(View.GONE);recyclerViewMenuEntree.setVisibility(View.VISIBLE);
            recyclerViewMenuDesert.setVisibility(View.GONE);recyclerViewMenuBeverages.setVisibility(View.GONE);
            initDataMenuEntree();
            initRecyclerViewMenuEntree();
        }

        else if(category.equals("Desserts")) {
            recyclerViewMenuMainCourse.setVisibility(View.GONE);recyclerViewMenuAppetizers.setVisibility(View.GONE);recyclerViewMenuEntree.setVisibility(View.GONE);
            recyclerViewMenuDesert.setVisibility(View.VISIBLE);recyclerViewMenuBeverages.setVisibility(View.GONE);
            initDataMenuDesert();
            initRecyclerViewMenuDesert();
        }
        else if (category.equals("Beverages")) {
            recyclerViewMenuMainCourse.setVisibility(View.GONE);recyclerViewMenuAppetizers.setVisibility(View.GONE);recyclerViewMenuEntree.setVisibility(View.GONE);
            recyclerViewMenuDesert.setVisibility(View.GONE);recyclerViewMenuBeverages.setVisibility(View.VISIBLE);
            initDataMenuBeverages();
            initRecyclerViewMenuBeverages();
        }
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}