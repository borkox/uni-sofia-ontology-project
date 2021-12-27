package bg.unisofia.bsmarkov.ont;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionLocal;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.reasoner.rulesys.Rule.Parser;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    /**
     * Loads rules from class path file.
     */
    public static List<Rule> readRules() throws IOException {

        try (InputStream resourceAsStream = CriminalDeduction.class.getResourceAsStream("/rules.txt");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
            return Rule.parseRules(Rule.rulesParserFromReader(reader));
        }
    }

    /**
     * Reads resource as string from class path.
     */
    public static String readResource(String classPathResource) throws IOException {

        try (InputStream resourceAsStream = CriminalDeduction.class.getResourceAsStream(classPathResource);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Saves the generated ontology in TURTLE, RDF or other formats specified by <code>format</code>
     *
     * @param fileName is relative or full path for output file.
     * @param format is one of predefined values: "RDF/XML", * "RDF/XML-ABBREV", "N-TRIPLE" and "TURTLE".  The default
     * value, * represented by <code>null</code> is "RDF/XML".
     * @throws IOException if file cannot be created.
     */
    public static void saveOntology(String fileName, String format, Model inf) throws IOException {
        log.info("Saving {} into {}", format, fileName);
        try (Writer fw = new FileWriter(fileName)) {
            inf.write(fw, format);
        }
    }

    /**
     * Runs a SPARQL command that is in classpath file and print the result on the log.
     */
    public static void sparqlLogResults(Model inf, String queryString, String identifyQuery) {
        log.info("===========================================");
        log.info("Result from query: {}", identifyQuery);
        try (RDFConnection conn = new RDFConnectionLocal(new DatasetImpl(inf))) {

            conn.querySelect(
                    queryString
                    , (qs) -> {
                        log.info("----------------");
                        Iterator<String> stringIterator = qs.varNames();
                        while (stringIterator.hasNext()) {
                            String varName = stringIterator.next();
                            Resource subject = qs.getResource(varName);
                            log.info("Subject[{}]: {}}", varName, subject);
                        }
                    });
        }
        log.info("===========================================");
    }
}
