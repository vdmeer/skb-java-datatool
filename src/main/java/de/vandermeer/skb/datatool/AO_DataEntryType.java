package de.vandermeer.skb.datatool;

import java.util.TreeSet;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.execs.options.AbstractApplicationOption;
import de.vandermeer.skb.datatool.commons.DataTarget;
import de.vandermeer.skb.datatool.commons.StandardDataEntryTypes;

public class AO_DataEntryType extends AbstractApplicationOption<String> {

	/**
	 * Returns the new option.
	 * @throws NullPointerException - if description parameter is null
	 * @throws IllegalArgumentException - if description parameter is empty
	 */
	public AO_DataEntryType(){
		super("specifies the type the tool should process", "###");

		Option.Builder builder = Option.builder("e");
		builder.longOpt("entry-type");
		builder.required(true);
		builder.hasArg().argName("TYPE");
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

		ret.append("This options directs the data tool to process a particular entry type. ");
		ret.append("The tool will then load all required entry types and de-reference any SKB links. ");
		ret.appendNewLine();

		ret.append("Available targets for output generation depend on the type, not all types support all targets.");
		ret.appendNewLine();

		ret.append("Available types are: ");
		for(StandardDataEntryTypes type : StandardDataEntryTypes.values()){
			ret.append(" - ").append(type.getType());
			ret.append(" -> supporting targets: ");
			TreeSet<String> st = new TreeSet<>();
			for(DataTarget target : type.getSupportedTargets()){
				st.add(target.getTargetName());
			}
			ret.appendWithSeparators(st, ", ");
			ret.appendNewLine();
		}

		ret.appendNewLine();
		return ret.toString();
	}
}
