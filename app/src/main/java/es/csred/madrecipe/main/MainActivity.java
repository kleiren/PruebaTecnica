package es.csred.madrecipe.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.csred.madrecipe.R;
import es.csred.madrecipe.recipe.RecipeActivity;
import es.csred.madrecipe.http.RecipePuppyAPI;
import es.csred.madrecipe.http.apimodel.Result;
import es.csred.madrecipe.root.App;


public class MainActivity extends AppCompatActivity implements MainActivityMVP.View {

    @Inject
    MainActivityMVP.Presenter presenter;
    @Inject
    RecipePuppyAPI recipePuppyAPI;


    @BindView(R.id.card_recycler_view_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.main_txtNoRecipes)
    TextView txtNoRecipes;

    private List<Result> results;
    private RecipeDataAdapter adapter;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        initViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();

        search(searchView);
        return true;

    }

    @Override
    public void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")) {

                    int size = results.size();
                    results.clear();
                    adapter.notifyItemRangeRemoved(0, size);
                    txtNoRecipes.setVisibility(View.VISIBLE);

                } else {
                    // Sends the request down to the model
                    presenter.searchRequest(newText);

                    txtNoRecipes.setVisibility(View.GONE);
                }

                return true;
            }
        });
    }

    @Override
    public void setRecipes(List<Result> results) {

        this.results = results;
        if (results.isEmpty()) txtNoRecipes.setVisibility(View.VISIBLE);
        else txtNoRecipes.setVisibility(View.GONE);

        adapter = new RecipeDataAdapter(results, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initViews() {

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    result = results.get(position);
                    startRecipeActivity(result);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }


    @Override
    public void startRecipeActivity(Result result) {
        Intent intent = new Intent(getApplication(), RecipeActivity.class);
        intent.putExtra("result", result);
        startActivityForResult(intent, 1);
    }


}
