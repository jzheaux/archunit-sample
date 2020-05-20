package sample;


import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import org.junit.Test;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchitectureTests {

	@Test
	public void freeOfCycles() {
		String pkg = "sample";
		JavaClasses classes = new ClassFileImporter().importPackages(pkg);
		slices().assignedFrom(this.fullPackageSlices).should().beFreeOfCycles()
				.check(classes);
	}

	@Test
	public void freeOfCyclesMatching() {
		String pkg = "sample";
		JavaClasses classes = new ClassFileImporter().importPackages(pkg);
		slices().matching("..(*)..").should().beFreeOfCycles()
				.check(classes);
	}

	SliceAssignment fullPackageSlices = new SliceAssignment() {
		@Override
		public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
			return SliceIdentifier.of(javaClass.getPackageName());
		}

		@Override
		public String getDescription() {
			return "full package slices";
		}
	};
}

