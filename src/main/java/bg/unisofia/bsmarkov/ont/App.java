package bg.unisofia.bsmarkov.ont;

import java.io.IOException;
import org.apache.jena.ontology.OntModel;

public class App {

    public static void main(String[] args) throws IOException {
        OntModel ont1 = CriminalStory.describeCriminalStory();
        Utils.saveOntology("gen-ontology/soap-ontology-initial.ttl", "TURTLE", ont1);
        OntModel ont2 = CriminalInference.makeDeductions(ont1);
        Utils.saveOntology("gen-ontology/soap-ontology-inference.ttl", "TURTLE", ont2);
        Utils.saveOntology("gen-ontology/soap-ontology-inference.xml", "RDF/XML", ont2);

        CriminalDeduction.criminalDeduction(ont2);
    }
}
