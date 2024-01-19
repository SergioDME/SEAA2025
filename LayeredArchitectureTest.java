import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "project")
public class LayeredArchitectureTest {

    @ArchTest
    public static final ArchRule model_dependencies_are_respected = layeredArchitecture()
            .layer("View").definedBy("project.GUI..")
            .layer("Controller").definedBy("project.CONTROLLER..")
            .layer("Model").definedBy("project.MODEL..")
            .layer("Database").definedBy("project.DAO..")
            .whereLayer("Model").mayNotBeAccessedByAnyLayer();
    @ArchTest

    public static final ArchRule view_dependencies_are_respected = layeredArchitecture()
            .layer("View").definedBy("project.GUI..")
            .layer("Controller").definedBy("project.CONTROLLER..")
            .layer("Model").definedBy("project.MODEL..")
            .layer("Database").definedBy("project.DAO..")
            .whereLayer("View").mayNotBeAccessedByAnyLayer();
    @ArchTest
    public static final ArchRule controller_dependencies_are_respected = layeredArchitecture()
            .layer("View").definedBy("project.GUI..")
            .layer("Controller").definedBy("project.CONTROLLER..")
            .layer("Model").definedBy("project.MODEL..")
            .layer("Database").definedBy("project.DAO..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer();
    @ArchTest
    public static final ArchRule db_dependencies_are_respected = layeredArchitecture()
            .layer("View").definedBy("project.GUI..")
            .layer("Controller").definedBy("project.Business_Logic..")
            .layer("Model").definedBy("project.MODEL..")
            .layer("Database").definedBy("project.DAO..")
            .whereLayer("Database").mayNotBeAccessedByAnyLayer();

    @ArchTest
    static final ArchRule view_loop = classes()
            .that().resideInAPackage("project.GUI")
            .should().dependOnClassesThat(
                    JavaClass.Predicates.resideOutsideOfPackage("project.GUI")
            );
    @ArchTest
    static final ArchRule model_loop = classes()
            .that().resideInAPackage("project.MODEL")
            .should().dependOnClassesThat(
                    JavaClass.Predicates.resideOutsideOfPackage("project.MODEL")
            );

    @ArchTest
    static final ArchRule controller_loop = classes()
            .that().resideInAPackage("project.CONTROLLER")
            .should().dependOnClassesThat(
                    JavaClass.Predicates.resideOutsideOfPackage("project.CONTROLLER")
            );

    @ArchTest
    static final ArchRule database_loop = classes()
            .that().resideInAPackage("project.DAO")
            .should().dependOnClassesThat(
                    JavaClass.Predicates.resideOutsideOfPackage("project.DAO")
            );

}
