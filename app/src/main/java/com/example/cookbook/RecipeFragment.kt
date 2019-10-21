package com.example.cookbook

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.model.Recipe
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [RecipeFragment.OnListFragmentInteractionListener] interface.
 */
class RecipeFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null
    private val recipes = Firebase.firestore.collection("recipes")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    private fun firebaseData(recyclerView: RecyclerView) {

        val option = FirestoreRecyclerOptions.Builder<Recipe>()
            .setQuery(recipes, Recipe::class.java)
            .build()
        val adapter = object : FirestoreRecyclerAdapter<Recipe, RecipeViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_recipe, parent, false)
                return RecipeViewHolder(view)
            }

            override fun onBindViewHolder(holder: RecipeViewHolder, position: Int, model: Recipe) {
                holder.mTitleView.text = model.title
//                holder.mDescriptionView.text = model.prepTime.toString()
                val storage = FirebaseStorage.getInstance()
                if (model.image.isNotEmpty()) {
                    val image = storage.reference.child(model.image)
                    Glide.with(holder.mView /* context */)
                        .load(image)
                        .into(holder.mImageView)
                }
                holder.mView.tag = model
                holder.mView.setOnClickListener { view -> listener?.onListFragmentInteraction(view.tag as Recipe) }
            }
        }
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                firebaseData(view)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Recipe?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
