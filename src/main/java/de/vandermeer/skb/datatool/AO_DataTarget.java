package de.vandermeer.skb.datatool;

import java.util.TreeSet;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.execs.options.AbstractApplicationOption;
import de.vandermeer.skb.datatool.commons.DataEntryType;
import de.vandermeer.skb.datatool.commons.StandardDataTargets;

public class AO_DataTarget extends AbstractApplicationOption<String> {

	/**
	 * Returns the new option.
	 * @param required true if option is required, false of it is optional
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_DataTarget(boolean required){
		super("specifies a target for output generation and character conversion", "###");

		Option.Builder builder = Option.builder("t");
		builder.longOpt("target");
		builder.hasArg().argName("TARGET");
		builder.required(required);
		this.setCliOption(builder.build());
	}

	@Override
	public String convertValue(Object value) {
		if(value==null){
			return null;
		}
		return value.toString();
	}

	@Override
	public String getDescriptionLong(){
		StrBuilder ret = new StrBuilder(50);

		ret.append("This options sets the target for the data tool. ");
		ret.append("A target is important for two things: character conversion and output generation. ");
		ret.append("Since most text is given as UTF-8, targets such as HTML and LaTeX will need a conversion of some characters into their specific format. ");
		ret.append("For auto-generating output, the target will have all information required to generate a file in the target language, for instance file extension, special rules for character translation, and a template for output generation. ");
		ret.appendNewLine();

		ret.append("Targets support specific data entry types. ");
		ret.append("Not all targets support all data entry types. ");
		ret.append("The selected target must support the selected data entry type. ");
		ret.appendNewLine();

		ret.append("Available targets are: ");
		for(StandardDataTargets target : StandardDataTargets.values()){
			ret.append(" - ").append(target.getTargetName());
			ret.append(" -> supporting types: ");
			TreeSet<String> st = new TreeSet<>();
			for(DataEntryType type : target.getSupportedTypes()){
				st.add(type.getType());
			}
			ret.appendWithSeparators(st, ", ");
			ret.appendNewLine();
		}

		ret.appendNewLine();
		return ret.toString();
	}
}
