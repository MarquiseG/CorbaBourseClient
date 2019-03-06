import bourse.Bourse;
import bourse.BourseHelper;
import bourse.CCotation;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class ClientCorba
{
    public static void main(String[] args)
    {
        try

        {
            //Initialiser ORB
            ORB lORB = ORB.init(args, null);
            //Créer le context de naming service
            NamingContext lContext = null;
            lContext = NamingContextHelper.narrow(lORB.resolve_initial_references("NameService"));

            //Créer un tableau de noms
            NameComponent[] lNameComponents = new NameComponent[1];
            //Initialiser le nom de l'objet distant
            lNameComponents[0] = new NameComponent("BOURSE", "");
            //Récupérer la référence de l'objet distant à partir du naming service
            Object lObject = lContext.resolve(lNameComponents);
            Bourse stub = BourseHelper.narrow(lObject);
            System.out.println("Moyenne de contation de SGMP" + stub.getMoyennesCotations("SGMP"));
            CCotation[] lSGMPS = stub.getCotations("SGMP");
            for (CCotation lSGMP : lSGMPS)
            {
                System.out.println(lSGMP.dateCotation + "-------" + lSGMP.valAction);
            }
        }
        catch (CannotProceed | InvalidName | NotFound | org.omg.CosNaming.NamingContextPackage.InvalidName aInCannotProceed)
        {
            aInCannotProceed.printStackTrace();
        }
    }

}
