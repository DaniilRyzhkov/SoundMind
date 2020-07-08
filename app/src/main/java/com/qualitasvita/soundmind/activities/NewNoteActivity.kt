package com.qualitasvita.soundmind.activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qualitasvita.soundmind.R
import com.qualitasvita.soundmind.adapters.NewNotePagerAdapter
import com.qualitasvita.soundmind.fragments.*
import com.qualitasvita.soundmind.model.Note
import com.qualitasvita.soundmind.presenters.NewNotePresenter
import com.qualitasvita.soundmind.views.NewNoteView
import com.rd.PageIndicatorView
import java.util.*

/**
 * Активити для создания новой записи.
 *
 */
class NewNoteActivity : MvpAppCompatActivity(), NewNoteView {
    @InjectPresenter
    lateinit var newNotePresenter: NewNotePresenter

    @ProvidePresenter
    fun provideNewNotePresenter() = NewNotePresenter()

    lateinit var mmenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.fabSaveNote)
        floatingActionButton.visibility = View.GONE
        floatingActionButton.setOnClickListener { newNotePresenter.saveNote() }

        // BottomAppBar initiate
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_new_note)
        setSupportActionBar(bottomAppBar)

        // Viewpager initiate
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        val adapter = NewNotePagerAdapter(supportFragmentManager, lifecycle, stepsArray)
        viewPager.adapter = adapter

        // PageIndicator initiate
        val pageIndicatorView = findViewById<PageIndicatorView>(R.id.pageIndicatorView_new_note)
        pageIndicatorView.count = 6
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 5) {
                    bottomAppBar.fabAnimationMode = BottomAppBar.FAB_ANIMATION_MODE_SCALE
                    floatingActionButton.visibility = View.VISIBLE
                }
            }

            override fun onPageSelected(position: Int) {
                pageIndicatorView.selection = position
                bottomAppBar.performShow();

                // Hide Keyboard
                val hideKeyboardToken: EditText = findViewById(R.id.hide_keyboard_token)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(hideKeyboardToken.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                when (position) {
                    3 -> {
                        mmenu.findItem(R.id.mistakes).isVisible = false
                    }
                    4 -> {
                        mmenu.findItem(R.id.mistakes).isVisible = true
                        mmenu.findItem(R.id.positive).isVisible = false
                    }
                    5 -> {
                        mmenu.findItem(R.id.mistakes).isVisible = false
                        mmenu.findItem(R.id.positive).isVisible = true
                    }
                }
            }
        })
    }

    private val stepsArray: ArrayList<Fragment>
        get() {
            val steps = ArrayList<Fragment>()
            steps.add(0, Step1_SituationFragment())
            steps.add(1, Step2_EmotionFragment())
            steps.add(2, Step3_ThoughtFragment())
            steps.add(3, Step4_ActionFragment())
            steps.add(4, Step5_ResponseFragment())
            steps.add(5, Step6_ResultFragment())
            return steps
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_note, menu);
        mmenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val builder = AlertDialog.Builder(this@NewNoteActivity)
            builder.setMessage(R.string.alert_back)
            builder.setCancelable(true)

            // Подготовка к удалению текущей записи.
            builder.setPositiveButton(R.string.button_ok) { _: DialogInterface?, _: Int -> finish() }

            // Отмена удаления
            builder.setNegativeButton(R.string.button_cancel) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        //
        if (item.itemId == R.id.help) {
            MaterialDialog(this).show {
                title(R.string.label_help_new_note)
                message(R.string.help_new_note)
                positiveButton(R.string.button_ok)
                //negativeButton(R.string.disagree)
                //debugMode(debugMode)
                //lifecycleOwner(this@MainActivity)
            }
        }

        //
        if (item.itemId == R.id.mistakes) {
            startActivity(Intent(this, MistakesActivity::class.java))
        }

        //
        if (item.itemId == R.id.positive) {
            MaterialDialog(this).show {
                title(R.string.label_positive)
                listItemsMultiChoice(
                        R.array.positive_emotions, initialSelection = intArrayOf()
                ) { _, _, text ->
                    Toast.makeText(context, R.string.toast_changes_saved, Toast.LENGTH_LONG).show()
                    newNotePresenter.positiveChecked(text.joinToString("\n"))
                }
                positiveButton(R.string.button_ok)
                negativeButton(R.string.button_cancel)
            }
        }

        return true
    }

    override fun saveNote(note: Note) {
        //Log.d("TAG", note.situationText)
        val intent = Intent()
        intent.putExtra(Note::class.java.simpleName, note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun showPositiveDialog(view: View?) {
        MaterialDialog(this).show {
            title(R.string.label_positive)
            listItemsMultiChoice(
                    R.array.positive_emotions, initialSelection = intArrayOf()
            ) { _, _, text ->
                Toast.makeText(context, R.string.toast_changes_saved, Toast.LENGTH_LONG).show()
                newNotePresenter.positiveChecked(text.joinToString("\n"))
            }
            positiveButton(R.string.button_ok)
            negativeButton { R.string.button_cancel }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newNotePresenter.reset()
    }
}