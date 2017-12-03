package es.csred.madrecipe.main;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.csred.madrecipe.R;
import es.csred.madrecipe.http.apimodel.Result;
import es.csred.madrecipe.root.GlideApp;

public class RecipeDataAdapter extends RecyclerView.Adapter<RecipeDataAdapter.ViewHolder> {
    private List<Result> results;
    private Context context;


    public RecipeDataAdapter(List<Result> results, Context context) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecipeDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeDataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtRecipeName.setText(results.get(i).getTitle());
        viewHolder.txtRecipeIngredients.setText(results.get(i).getIngredients());
        viewHolder.txtRecipeSource.setText(results.get(i).getHref());

        GlideApp.with(context)
                .load(results.get(i).getThumbnail())
                .placeholder(R.drawable.fork)
                .fallback(R.drawable.fork)
                .into(viewHolder.imgRecipe);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_row_txtRecipeTitle) TextView txtRecipeName;
        @BindView(R.id.recipe_row_txtRecipeIngredients) TextView txtRecipeIngredients;
        @BindView(R.id.recipe_row_txtRecipeSource) TextView txtRecipeSource;
        @BindView(R.id.recipe_row_imgRecipe) ImageView imgRecipe;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }

}