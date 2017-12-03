/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.csred.madrecipe.recipe;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.csred.madrecipe.R;
import es.csred.madrecipe.http.apimodel.Result;
import es.csred.madrecipe.main.MainActivityMVP;
import es.csred.madrecipe.root.App;
import es.csred.madrecipe.root.GlideApp;

public class RecipeActivity extends AppCompatActivity implements RecipeActivityMVP.View, ObservableScrollViewCallbacks {

    @Inject
    RecipeActivityMVP.Presenter presenter;

    // (Observable scrollview stuff)
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    @BindView(R.id.recipe_imgRecipe)
    ImageView imgRecipe;
    @BindView(R.id.recipe_txtIngredients)
    TextView txtIngredients;
    @BindView(R.id.recipe_txtWebSource)
    TextView txtWebSource;
    @BindView(R.id.overlay)
    View mOverlayView;
    @BindView(R.id.recipe_scrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        Result result = (Result) getIntent().getSerializableExtra("result");

        // (Observable scrollview stuff)
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarSize = getActionBarSize();
        mScrollView.setScrollViewCallbacks(this);
        setTitle(null);
        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                onScrollChanged(0, false, false);
            }
        });

        // Tapping on the back button in the toolbar goes back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeActivity.super.onBackPressed();
            }
        });

        fillInfo(result);
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
    }


    @Override
    public void fillInfo(Result result) {
        titleView.setText(result.getTitle());
        txtIngredients.setText(result.getIngredients());
        txtWebSource.setText(result.getHref());

        GlideApp.with(this)
                .load(result.getThumbnail())
                .placeholder(R.drawable.fork)
                .fallback(R.drawable.fork)
                .into(imgRecipe);
    }

    // ObservableScrollView stuff
    @Override
    public int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    // ObservableScrollView stuff
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imgRecipe, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(titleView, 0);
        ViewHelper.setPivotY(titleView, 0);
        ViewHelper.setScaleX(titleView, scale);
        ViewHelper.setScaleY(titleView, scale);

        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - titleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(titleView, titleTranslationY);

    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

}
