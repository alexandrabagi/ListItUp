package com.bignerdranch.android.listitup.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.fragments.ShoppingListFragment;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;

// https://stackoverflow.com/questions/34609191/why-itemtouchhelper-callbacks-onchilddraw-will-be-called-after-clearview
// source: https://github.com/kitek/android-rv-swipe-delete

public class MyItemTouchCallback extends ItemTouchHelper.Callback {

        private ShoppingListFragment.ShopItemAdapter adapter;
        private ItemVM mItemVM;
        private Context context;

        public MyItemTouchCallback(Context context, ShoppingListFragment.ShopItemAdapter adapter, ItemVM viewModel) {
            this.adapter = adapter;
            this.mItemVM = viewModel;
            this.context = context;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            //But where to remove from???
            int position = viewHolder.getAdapterPosition();
            Item itemToRemove = adapter.getItems().get(position);
            mItemVM.deleteFromShop(itemToRemove);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            View itemView = viewHolder.itemView;
            int itemHeight = itemView.getBottom() - itemView.getTop();
            boolean isCanceled = dX == 0f && !isCurrentlyActive;

            if (isCanceled) {
                clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                return;
            }

            // if dX > 0 then swiping right
            if (dX > 0) {
                // Setting up into cart swipe
                GradientDrawable backgroundIntoCart = new GradientDrawable();
                backgroundIntoCart.setShape(GradientDrawable.RECTANGLE);
                int colorCartBg = Color.parseColor("#00BCD4");
                backgroundIntoCart.setColor(colorCartBg);
                float[] radii = {50, 50, 0, 0, 0, 0, 50, 50}; // TODO: get the number programmatically
                backgroundIntoCart.setCornerRadii(radii);
                backgroundIntoCart.setBounds(
                        itemView.getLeft(),
                        itemView.getTop(),
                        itemView.getRight() - 50,
                        itemView.getBottom()
                );
                backgroundIntoCart.draw(c);

                // Calculate position of cart icon
                Drawable cartIcon = ContextCompat.getDrawable(context, R.drawable.ic_cart_active);
                cartIcon.setTint(Color.parseColor("#FFFFFF"));
                int inHeightCart = cartIcon.getIntrinsicHeight();
                int inWidthCart = cartIcon.getIntrinsicWidth();
                int cartIconTop = itemView.getTop() + (itemHeight - inHeightCart) / 2;
                int cartIconMargin = (itemHeight - inHeightCart) / 2;
                int cartIconLeft = itemView.getLeft() + cartIconMargin;
                int cartIconRight = itemView.getLeft() + cartIconMargin + inWidthCart;
                int cartIconBottom = cartIconTop + inHeightCart;

                // Draw the delete icon
                cartIcon.setBounds(cartIconLeft, cartIconTop, cartIconRight, cartIconBottom);
                cartIcon.draw(c);
            } else {
                // Setting up delete swipe
                GradientDrawable backgroundDelete = new GradientDrawable();
                backgroundDelete.setShape(GradientDrawable.RECTANGLE);
                int color = Color.parseColor("#FF0C3E");
                backgroundDelete.setColor(color);
                float[] radiiDel = {0, 0, 50, 50, 50, 50, 0, 0}; // TODO: get the number programmatically
                backgroundDelete.setCornerRadii(radiiDel);
                backgroundDelete.setBounds(
                        itemView.getRight() + (int) dX - 50,
                        itemView.getTop(),
                        itemView.getRight(),
                        itemView.getBottom()
                );
                backgroundDelete.draw(c);

                // Calculate position of delete icon
                Drawable deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_bin);
                int inHeightDel = deleteIcon.getIntrinsicHeight();
                int inWidthDel = deleteIcon.getIntrinsicWidth();
                int deleteIconTop = itemView.getTop() + (itemHeight - inHeightDel) / 2;
                int deleteIconMargin = (itemHeight - inHeightDel) / 2;
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - inWidthDel;
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                int deleteIconBottom = deleteIconTop + inHeightDel;

                // Draw the delete icon
                deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                deleteIcon.draw(c);
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
            c.drawRect(left, top, right, bottom, clearPaint());
        }

        private Paint clearPaint() {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return paint;
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setElevation(viewHolder.itemView, 0);
        }
}