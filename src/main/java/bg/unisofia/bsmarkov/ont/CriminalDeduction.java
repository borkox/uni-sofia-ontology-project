package bg.unisofia.bsmarkov.ont;

import java.io.IOException;
import org.apache.jena.ontology.OntModel;

public class CriminalDeduction {

    public static void criminalDeduction(OntModel ontModel) throws IOException {

        // In here we want to discover is there a connection between
        // source of leak and the destination of the leak.
        Utils.sparqlLogResults(ontModel,
                Utils.readResource("/sparql_confirm_leak_info.txt"),
                "confirm leak of information");

        Utils.sparqlLogResults(ontModel,
                Utils.readResource("/sparql_identify_leak_from_project.txt"),
                "where is leak of info from project");

        Utils.sparqlLogResults(ontModel,
                Utils.readResource("/sparql_identify_leak_from_company.txt"),
                "where is leak of info from company");


    }

}
