package in.co.appadda.brainteaser.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.DataApplication;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.puzzles;

public class RetrieveRecordActivity extends Activity {
    private TextView titleView;
    private EditText codeView;
    private Button runCodeButton, sendCodeButton;

    private String code;
    private String table;
    private String type;

    private static BackendlessCollection resultCollection;
    private static Object resultObject;

    private String selectedProperty;
    private String selectedRelatedTable;
    private String selectedRelatedProperty;
    private String relation;
    private String[] relatedProperties;

    public static BackendlessCollection getResultCollection() {
        return resultCollection;
    }

    public static Object getResultObject() {
        return resultObject;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_code_template);

        DataApplication dataApplication = (DataApplication) getApplication();
        table = dataApplication.getChosenTable();

        type = getIntent().getStringExtra("type");

        initUI();
    }

    private void initUI() {
        titleView = (TextView) findViewById(R.id.sampleCodeTitle);
        codeView = (EditText) findViewById(R.id.sampleCodeEdit);
        runCodeButton = (Button) findViewById(R.id.runCodeButton);
        sendCodeButton = (Button) findViewById(R.id.sendCodeButton);

        String title = String.format("Retrieve Title", table);
        titleView.setText(title);
        if (table.equals("puzzles")) {
            code = getPuzzlesRetrievalCode();
        } else if (table.equals("aptitude")) {
            code = getAptitudeRetrievalCode();
        }

        codeView.setText(code);

        runCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRunCodeButtonClicked();
            }
        });

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendCodeButtonClicked();
            }
        });
    }

    private void onRunCodeButtonClicked() {
        if (table.equals("puzzles")) {
            retrievePuzzlesRecord();
        } else if (table.equals("aptitude")) {
            retrieveAptitudeRecord();
        }
    }

    private void onSendCodeButtonClicked() {
        Intent nextIntent = new Intent(getBaseContext(), SendEmailActivity.class);
        nextIntent.putExtra("code", code);
        nextIntent.putExtra("table", table);
        nextIntent.putExtra("method", type);
        startActivity(nextIntent);
    }

    private String getPuzzlesRetrievalCode() {
        if (type.equals("Basic Find")) {
            return getBasicPuzzlesRetrievalCode();
        } else if (type.equals("Find First")) {
            return getFirstPuzzlesRetrievalCode();
        } else if (type.equals("Find Last")) {
            return getLastPuzzlesRetrievalCode();
        } else if (type.equals("Find with Sort")) {
            return getSortedPuzzlesRetrievalCode();
        } else if (type.equals("Find with Relations")) {
            return getRelatedPuzzlesRetrievalCode();
        }
        return null;
    }

    private void retrievePuzzlesRecord() {
        if (type.equals("Basic Find")) {
            retrieveBasicPuzzlesRecord();
        } else if (type.equals("Find First")) {
            retrieveFirstPuzzlesRecord();
        } else if (type.equals("Find Last")) {
            retrieveLastPuzzlesRecord();
        } else if (type.equals("Find with Sort")) {
            retrieveSortedPuzzlesRecord();
        } else if (type.equals("Find with Relations")) {
            retrieveRelatedPuzzlesRecord();
        }
    }

    private String getBasicPuzzlesRetrievalCode() {
        return "BackendlessDataQuery query = new BackendlessDataQuery();\n"
                + "puzzles.findAsync( query, new AsyncCallback<BackendlessCollection<puzzles>>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( BackendlessCollection<puzzles> response )\n"
                + "  {\n"
                + "    puzzles firstPuzzles = response.getCurrentPage().get( 0 );\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveBasicPuzzlesRecord() {
        BackendlessDataQuery query = new BackendlessDataQuery();
        puzzles.findAsync(query, new DefaultCallback<BackendlessCollection<puzzles>>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);

                resultCollection = response;

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                builder.setTitle("Property to show:");
                final String[] properties = {"hint", "question", "ownerId", "answer", "_id", "objectId", "solution", "created", "updated"};
                builder.setItems(properties, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowByPropertyActivity.class);
                        nextIntent.putExtra("type", type);
                        nextIntent.putExtra("property", properties[i]);
                        startActivity(nextIntent);
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private String getFirstPuzzlesRetrievalCode() {
        return "puzzles.findFirstAsync( new AsyncCallback<puzzles>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( puzzles object )\n"
                + "  {\n"
                + "    //work with the object\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveFirstPuzzlesRecord() {
        puzzles.findFirstAsync(new DefaultCallback<puzzles>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(puzzles response) {
                super.handleResponse(response);
                resultObject = response;
                Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowEntityActivity.class);
                nextIntent.putExtra("type", type);
                startActivity(nextIntent);
            }
        });
    }

    private String getLastPuzzlesRetrievalCode() {
        return "puzzles.findLastAsync( new AsyncCallback<puzzles>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( puzzles object )\n"
                + "  {\n"
                + "    //work with the object\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveLastPuzzlesRecord() {
        puzzles.findLastAsync(new DefaultCallback<puzzles>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(puzzles response) {
                super.handleResponse(response);
                resultObject = response;
                Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowEntityActivity.class);
                nextIntent.putExtra("type", type);
                startActivity(nextIntent);
            }
        });
    }

    private String getSortedPuzzlesRetrievalCode() {
        return "QueryOptions queryOptions = new QueryOptions();\n"
                + "List<String> sortByProperties = new ArrayList<String>();\n"
                + "sortByProperties.add( \"someProperty\" );\n"
                + "queryOptions.setSortBy( sortByProperties );\n"
                + "BackendlessDataQuery query = new BackendlessDataQuery(  );\n"
                + "query.setQueryOptions( queryOptions );\n"
                + "puzzles.findAsync( query, new AsyncCallback<BackendlessCollection<puzzles>>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( BackendlessCollection<puzzles> response )\n"
                + "  {\n"
                + "    puzzles firstSortedPuzzles = response.getCurrentPage().get( 0 );\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveSortedPuzzlesRecord() {
        List<CharSequence> selectedItems = getIntent().getCharSequenceArrayListExtra("selectedProperties");
        QueryOptions queryOptions = new QueryOptions();
        List<String> sortByProperties = Arrays.asList(selectedItems.toArray(new String[selectedItems.size()]));
        queryOptions.setSortBy(sortByProperties);
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        puzzles.findAsync(query, new DefaultCallback<BackendlessCollection<puzzles>>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);

                resultCollection = response;

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                builder.setTitle("Property to show:");
                final String[] properties = {"hint", "question", "ownerId", "answer", "_id", "objectId", "solution", "created", "updated"};
                builder.setItems(properties, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowByPropertyActivity.class);
                        nextIntent.putExtra("type", type);
                        nextIntent.putExtra("property", properties[i]);
                        startActivity(nextIntent);
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private String getRelatedPuzzlesRetrievalCode() {
        return "QueryOptions queryOptions = new QueryOptions();\n"
                + "List<String> relationsToFind = new ArrayList<String>();\n"
                + "queryOptions.setRelated( relationsToFind );\n"
                + "BackendlessDataQuery query = new BackendlessDataQuery();\n"
                + "query.setQueryOptions( queryOptions );\n"
                + "puzzles.findAsync( query, new AsyncCallback<BackendlessCollection<puzzles>>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( BackendlessCollection<puzzles> collection )\n"
                + "  {\n"
                + "    //work with objects\n"
                + "  }\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "}";
    }

    private void retrieveRelatedPuzzlesRecord() {
        final List<CharSequence> selectedRelations = getIntent().getCharSequenceArrayListExtra("selectedRelations");
        final List<CharSequence> selectedRelatedTables = getIntent().getCharSequenceArrayListExtra("selectedRelatedTables");
        final String[] selectedRelationsArray = selectedRelations.toArray(new String[selectedRelations.size()]);
        final String[] selectedRelatedTablesArray = selectedRelatedTables.toArray(new String[selectedRelatedTables.size()]);
        QueryOptions queryOptions = new QueryOptions();
        List<String> relationsToFind = Arrays.asList(selectedRelationsArray);
        queryOptions.setRelated(relationsToFind);
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        puzzles.findAsync(query, new DefaultCallback<BackendlessCollection<puzzles>>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);
                resultCollection = response;

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                builder.setTitle("Property to show:");
                final String[] properties = {"hint", "question", "ownerId", "answer", "_id", "objectId", "solution", "created", "updated"};
                builder.setItems(properties, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedProperty = properties[i];

                        AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                        builder.setTitle("Related table to show:");
                        builder.setItems(selectedRelatedTablesArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedRelatedTable = selectedRelatedTablesArray[i];
                                relation = selectedRelationsArray[i];
                                if (selectedRelatedTable.equals("GeoPoint")) {
                                    relatedProperties = new String[]{"Latitude", "Longitude", "Metadata"};
                                }
                                dialogInterface.cancel();

                                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                                builder.setTitle("Related property to show:");
                                if (selectedRelatedTable.equals("puzzles")) {
                                    relatedProperties = new String[]{"hint", "question", "ownerId", "answer", "_id", "objectId", "solution", "created", "updated"};
                                } else if (selectedRelatedTable.equals("aptitude")) {
                                    relatedProperties = new String[]{"questions", "created", "option_three", "explanation", "option_two", "option_four", "updated", "ownerId", "answer", "_id", "option_one", "objectId"};
                                }

                                builder.setItems(relatedProperties, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        selectedRelatedProperty = relatedProperties[i];
                                        dialogInterface.cancel();
                                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowEntityActivity.class);
                                        nextIntent.putExtra("type", type);
                                        nextIntent.putExtra("property", selectedProperty);
                                        nextIntent.putExtra("relation", relation);
                                        nextIntent.putExtra("relatedTable", selectedRelatedTable);
                                        nextIntent.putExtra("relatedProperty", selectedRelatedProperty);
                                        startActivity(nextIntent);
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.create().show();
                            }
                        });
                        builder.create().show();
                    }
                });
                builder.create().show();
            }
        });
    }

    private String getAptitudeRetrievalCode() {
        if (type.equals("Basic Find")) {
            return getBasicAptitudeRetrievalCode();
        } else if (type.equals("Find First")) {
            return getFirstAptitudeRetrievalCode();
        } else if (type.equals("Find Last")) {
            return getLastAptitudeRetrievalCode();
        } else if (type.equals("Find with Sort")) {
            return getSortedAptitudeRetrievalCode();
        } else if (type.equals("Find with Relations")) {
            return getRelatedAptitudeRetrievalCode();
        }
        return null;
    }

    private void retrieveAptitudeRecord() {
        if (type.equals("Basic Find")) {
            retrieveBasicAptitudeRecord();
        } else if (type.equals("Find First")) {
            retrieveFirstAptitudeRecord();
        } else if (type.equals("Find Last")) {
            retrieveLastAptitudeRecord();
        } else if (type.equals("Find with Sort")) {
            retrieveSortedAptitudeRecord();
        } else if (type.equals("Find with Relations")) {
            retrieveRelatedAptitudeRecord();
        }
    }

    private String getBasicAptitudeRetrievalCode() {
        return "BackendlessDataQuery query = new BackendlessDataQuery();\n"
                + "aptitude.findAsync( query, new AsyncCallback<BackendlessCollection<aptitude>>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( BackendlessCollection<aptitude> response )\n"
                + "  {\n"
                + "    aptitude firstAptitude = response.getCurrentPage().get( 0 );\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveBasicAptitudeRecord() {
        BackendlessDataQuery query = new BackendlessDataQuery();
        aptitude.findAsync(query, new DefaultCallback<BackendlessCollection<aptitude>>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<aptitude> response) {
                super.handleResponse(response);

                resultCollection = response;

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                builder.setTitle("Property to show:");
                final String[] properties = {"questions", "created", "option_three", "explanation", "option_two", "option_four", "updated", "ownerId", "answer", "_id", "option_one", "objectId"};
                builder.setItems(properties, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowByPropertyActivity.class);
                        nextIntent.putExtra("type", type);
                        nextIntent.putExtra("property", properties[i]);
                        startActivity(nextIntent);
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private String getFirstAptitudeRetrievalCode() {
        return "aptitude.findFirstAsync( new AsyncCallback<aptitude>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( aptitude object )\n"
                + "  {\n"
                + "    //work with the object\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveFirstAptitudeRecord() {
        aptitude.findFirstAsync(new DefaultCallback<aptitude>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(aptitude response) {
                super.handleResponse(response);
                resultObject = response;
                Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowEntityActivity.class);
                nextIntent.putExtra("type", type);
                startActivity(nextIntent);
            }
        });
    }

    private String getLastAptitudeRetrievalCode() {
        return "aptitude.findLastAsync( new AsyncCallback<aptitude>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( aptitude object )\n"
                + "  {\n"
                + "    //work with the object\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveLastAptitudeRecord() {
        aptitude.findLastAsync(new DefaultCallback<aptitude>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(aptitude response) {
                super.handleResponse(response);
                resultObject = response;
                Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowEntityActivity.class);
                nextIntent.putExtra("type", type);
                startActivity(nextIntent);
            }
        });
    }

    private String getSortedAptitudeRetrievalCode() {
        return "QueryOptions queryOptions = new QueryOptions();\n"
                + "List<String> sortByProperties = new ArrayList<String>();\n"
                + "sortByProperties.add( \"someProperty\" );\n"
                + "queryOptions.setSortBy( sortByProperties );\n"
                + "BackendlessDataQuery query = new BackendlessDataQuery(  );\n"
                + "query.setQueryOptions( queryOptions );\n"
                + "aptitude.findAsync( query, new AsyncCallback<BackendlessCollection<aptitude>>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( BackendlessCollection<aptitude> response )\n"
                + "  {\n"
                + "    aptitude firstSortedAptitude = response.getCurrentPage().get( 0 );\n"
                + "  }\n"
                + "  @Override\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "} );";
    }

    private void retrieveSortedAptitudeRecord() {
        List<CharSequence> selectedItems = getIntent().getCharSequenceArrayListExtra("selectedProperties");
        QueryOptions queryOptions = new QueryOptions();
        List<String> sortByProperties = Arrays.asList(selectedItems.toArray(new String[selectedItems.size()]));
        queryOptions.setSortBy(sortByProperties);
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        aptitude.findAsync(query, new DefaultCallback<BackendlessCollection<aptitude>>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<aptitude> response) {
                super.handleResponse(response);

                resultCollection = response;

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                builder.setTitle("Property to show:");
                final String[] properties = {"questions", "created", "option_three", "explanation", "option_two", "option_four", "updated", "ownerId", "answer", "_id", "option_one", "objectId"};
                builder.setItems(properties, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowByPropertyActivity.class);
                        nextIntent.putExtra("type", type);
                        nextIntent.putExtra("property", properties[i]);
                        startActivity(nextIntent);
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private String getRelatedAptitudeRetrievalCode() {
        return "QueryOptions queryOptions = new QueryOptions();\n"
                + "List<String> relationsToFind = new ArrayList<String>();\n"
                + "queryOptions.setRelated( relationsToFind );\n"
                + "BackendlessDataQuery query = new BackendlessDataQuery();\n"
                + "query.setQueryOptions( queryOptions );\n"
                + "aptitude.findAsync( query, new AsyncCallback<BackendlessCollection<aptitude>>()\n"
                + "{\n"
                + "  @Override\n"
                + "  public void handleResponse( BackendlessCollection<aptitude> collection )\n"
                + "  {\n"
                + "    //work with objects\n"
                + "  }\n"
                + "  public void handleFault( BackendlessFault fault )\n"
                + "  {\n"
                + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
                + "  }\n"
                + "}";
    }

    private void retrieveRelatedAptitudeRecord() {
        final List<CharSequence> selectedRelations = getIntent().getCharSequenceArrayListExtra("selectedRelations");
        final List<CharSequence> selectedRelatedTables = getIntent().getCharSequenceArrayListExtra("selectedRelatedTables");
        final String[] selectedRelationsArray = selectedRelations.toArray(new String[selectedRelations.size()]);
        final String[] selectedRelatedTablesArray = selectedRelatedTables.toArray(new String[selectedRelatedTables.size()]);
        QueryOptions queryOptions = new QueryOptions();
        List<String> relationsToFind = Arrays.asList(selectedRelationsArray);
        queryOptions.setRelated(relationsToFind);
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        aptitude.findAsync(query, new DefaultCallback<BackendlessCollection<aptitude>>(RetrieveRecordActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<aptitude> response) {
                super.handleResponse(response);
                resultCollection = response;

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                builder.setTitle("Property to show:");
                final String[] properties = {"questions", "created", "option_three", "explanation", "option_two", "option_four", "updated", "ownerId", "answer", "_id", "option_one", "objectId"};
                builder.setItems(properties, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedProperty = properties[i];

                        AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                        builder.setTitle("Related table to show:");
                        builder.setItems(selectedRelatedTablesArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedRelatedTable = selectedRelatedTablesArray[i];
                                relation = selectedRelationsArray[i];
                                if (selectedRelatedTable.equals("GeoPoint")) {
                                    relatedProperties = new String[]{"Latitude", "Longitude", "Metadata"};
                                }
                                dialogInterface.cancel();

                                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
                                builder.setTitle("Related property to show:");
                                if (selectedRelatedTable.equals("puzzles")) {
                                    relatedProperties = new String[]{"hint", "question", "ownerId", "answer", "_id", "objectId", "solution", "created", "updated"};
                                } else if (selectedRelatedTable.equals("aptitude")) {
                                    relatedProperties = new String[]{"questions", "created", "option_three", "explanation", "option_two", "option_four", "updated", "ownerId", "answer", "_id", "option_one", "objectId"};
                                }

                                builder.setItems(relatedProperties, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        selectedRelatedProperty = relatedProperties[i];
                                        dialogInterface.cancel();
                                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowEntityActivity.class);
                                        nextIntent.putExtra("type", type);
                                        nextIntent.putExtra("property", selectedProperty);
                                        nextIntent.putExtra("relation", relation);
                                        nextIntent.putExtra("relatedTable", selectedRelatedTable);
                                        nextIntent.putExtra("relatedProperty", selectedRelatedProperty);
                                        startActivity(nextIntent);
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.create().show();
                            }
                        });
                        builder.create().show();
                    }
                });
                builder.create().show();
            }
        });
    }
}