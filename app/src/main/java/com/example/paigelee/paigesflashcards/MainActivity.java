package com.example.paigelee.paigesflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private final int NEW_CARD_REQUEST_CODE = 100;
    private final int EDIT_CARD_REQUEST_CODE = 200;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    Flashcard cardToEdit;

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Flashcard Database
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(0).getWrongAnswer2());
            ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(0).getAnswer());
        }


        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.Yellow));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.Yellow));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.Yellow));


            }

        });
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.colorPurpleBackground));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.colorPurpleBackground));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.colorPurpleBackground));
            }

        });
        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.WrongRed));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.RightGreen));

            }

        });

        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.RightGreen));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.WrongRed));
            }

        });
        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.RightGreen));


            }

        });

        findViewById(R.id.toggle_choices_invisibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.toggle_choices_invisibility).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_visibility).setVisibility(View.VISIBLE);
                findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer3).setVisibility(View.INVISIBLE);

            }
        });

        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.toggle_choices_invisibility).setVisibility(View.VISIBLE);
                findViewById(R.id.toggle_choices_visibility).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                findViewById(R.id.answer3).setVisibility(View.VISIBLE);

            }
        });


        Button addButton =
                (Button) findViewById(R.id.addButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        Button editButton =
                (Button) findViewById(R.id.editButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardToEdit = allFlashcards.get(currentCardDisplayedIndex);
                System.out.println("Hello this: " + cardToEdit.getQuestion());


                String question = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String correctAnswer = ((TextView) findViewById(R.id.flashcard_answer)).getText().toString();
                String wrongAnswer1 = ((TextView) findViewById(R.id.answer1)).getText().toString();
                String wrongAnswer2 = ((TextView) findViewById(R.id.answer2)).getText().toString();

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", question);
                intent.putExtra("answer", correctAnswer);
                intent.putExtra("wrong_answer1", wrongAnswer1);
                intent.putExtra("wrong_answer2", wrongAnswer2);
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });


        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex = getRandomNumber(0, allFlashcards.size() - 1);
                ;

                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);


                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        allFlashcards = flashcardDatabase.getAllCards();

        String question = data.getExtras().getString("question");
        String answer = data.getExtras().getString("answer");
        String wrongAnswer1 = data.getExtras().getString("wrong_answer1");
        String wrongAnswer2 = data.getExtras().getString("wrong_answer2");

        if (requestCode == 100 && resultCode == RESULT_OK) {


            ((TextView) findViewById(R.id.flashcard_question)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);

            ((TextView) findViewById(R.id.answer1)).setText(wrongAnswer1);
            ((TextView) findViewById(R.id.answer2)).setText(wrongAnswer2);
            ((TextView) findViewById(R.id.answer3)).setText(answer);

            flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
        }
        else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            cardToEdit.setWrongAnswer1(wrongAnswer1);
            cardToEdit.setWrongAnswer2(wrongAnswer2);

            flashcardDatabase.updateCard(cardToEdit);
        }

        allFlashcards = flashcardDatabase.getAllCards();

    }
}




