/*
 * generated by Xtext 2.12.0
 */
package de.darwinspl.constraint.dsl.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.darwinspl.services.ExpressionDslGrammarAccess;
import java.util.List;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class ConstraintDslGrammarAccess extends AbstractGrammarElementFinder {
	
	public class HyConstraintModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.darwinspl.constraint.dsl.ConstraintDsl.HyConstraintModel");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cHyConstraintModelAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cConstraintsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cConstraintsHyConstraintParserRuleCall_1_0 = (RuleCall)cConstraintsAssignment_1.eContents().get(0);
		
		//HyConstraintModel:
		//	{HyConstraintModel} constraints+=HyConstraint+;
		@Override public ParserRule getRule() { return rule; }
		
		//{HyConstraintModel} constraints+=HyConstraint+
		public Group getGroup() { return cGroup; }
		
		//{HyConstraintModel}
		public Action getHyConstraintModelAction_0() { return cHyConstraintModelAction_0; }
		
		//constraints+=HyConstraint+
		public Assignment getConstraintsAssignment_1() { return cConstraintsAssignment_1; }
		
		//HyConstraint
		public RuleCall getConstraintsHyConstraintParserRuleCall_1_0() { return cConstraintsHyConstraintParserRuleCall_1_0; }
	}
	public class HyConstraintElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.darwinspl.constraint.dsl.ConstraintDsl.HyConstraint");
		private final Assignment cRootExpressionAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cRootExpressionHyExpressionParserRuleCall_0 = (RuleCall)cRootExpressionAssignment.eContents().get(0);
		
		//HyConstraint:
		//	rootExpression=HyExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//rootExpression=HyExpression
		public Assignment getRootExpressionAssignment() { return cRootExpressionAssignment; }
		
		//HyExpression
		public RuleCall getRootExpressionHyExpressionParserRuleCall_0() { return cRootExpressionHyExpressionParserRuleCall_0; }
	}
	
	
	private final HyConstraintModelElements pHyConstraintModel;
	private final HyConstraintElements pHyConstraint;
	
	private final Grammar grammar;
	
	private final ExpressionDslGrammarAccess gaExpressionDsl;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public ConstraintDslGrammarAccess(GrammarProvider grammarProvider,
			ExpressionDslGrammarAccess gaExpressionDsl,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaExpressionDsl = gaExpressionDsl;
		this.gaTerminals = gaTerminals;
		this.pHyConstraintModel = new HyConstraintModelElements();
		this.pHyConstraint = new HyConstraintElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("de.darwinspl.constraint.dsl.ConstraintDsl".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public ExpressionDslGrammarAccess getExpressionDslGrammarAccess() {
		return gaExpressionDsl;
	}
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//HyConstraintModel:
	//	{HyConstraintModel} constraints+=HyConstraint+;
	public HyConstraintModelElements getHyConstraintModelAccess() {
		return pHyConstraintModel;
	}
	
	public ParserRule getHyConstraintModelRule() {
		return getHyConstraintModelAccess().getRule();
	}
	
	//HyConstraint:
	//	rootExpression=HyExpression;
	public HyConstraintElements getHyConstraintAccess() {
		return pHyConstraint;
	}
	
	public ParserRule getHyConstraintRule() {
		return getHyConstraintAccess().getRule();
	}
	
	//HyExpression expr::HyExpression:
	//	HyImpliesExpression;
	public ExpressionDslGrammarAccess.HyExpressionElements getHyExpressionAccess() {
		return gaExpressionDsl.getHyExpressionAccess();
	}
	
	public ParserRule getHyExpressionRule() {
		return getHyExpressionAccess().getRule();
	}
	
	//HyImpliesExpression expr::HyExpression:
	//	HyEquivalenceExpression ({expr::HyImpliesExpression.operand1=current} '->' operand2=HyEquivalenceExpression)*;
	public ExpressionDslGrammarAccess.HyImpliesExpressionElements getHyImpliesExpressionAccess() {
		return gaExpressionDsl.getHyImpliesExpressionAccess();
	}
	
	public ParserRule getHyImpliesExpressionRule() {
		return getHyImpliesExpressionAccess().getRule();
	}
	
	//HyEquivalenceExpression expr::HyExpression:
	//	HyAndExpression ({expr::HyEquivalenceExpression.operand1=current} '<->' operand2=HyAndExpression)*;
	public ExpressionDslGrammarAccess.HyEquivalenceExpressionElements getHyEquivalenceExpressionAccess() {
		return gaExpressionDsl.getHyEquivalenceExpressionAccess();
	}
	
	public ParserRule getHyEquivalenceExpressionRule() {
		return getHyEquivalenceExpressionAccess().getRule();
	}
	
	//HyAndExpression expr::HyExpression:
	//	HyOrExpression ({expr::HyAndExpression.operand1=current} '&&' operand2=HyOrExpression)*;
	public ExpressionDslGrammarAccess.HyAndExpressionElements getHyAndExpressionAccess() {
		return gaExpressionDsl.getHyAndExpressionAccess();
	}
	
	public ParserRule getHyAndExpressionRule() {
		return getHyAndExpressionAccess().getRule();
	}
	
	//HyOrExpression expr::HyExpression:
	//	TerminalExpression ({expr::HyOrExpression.operand1=current} '||' operand2=TerminalExpression)*;
	public ExpressionDslGrammarAccess.HyOrExpressionElements getHyOrExpressionAccess() {
		return gaExpressionDsl.getHyOrExpressionAccess();
	}
	
	public ParserRule getHyOrExpressionRule() {
		return getHyOrExpressionAccess().getRule();
	}
	
	//TerminalExpression expr::HyExpression:
	//	=> HyArithmeticalComparisonExpression | HyFeatureReferenceExpression | HyNestedExpression |
	//	HyConditionalFeatureReferenceExpression | HyNotExpression | HyBooleanValueExpression;
	public ExpressionDslGrammarAccess.TerminalExpressionElements getTerminalExpressionAccess() {
		return gaExpressionDsl.getTerminalExpressionAccess();
	}
	
	public ParserRule getTerminalExpressionRule() {
		return getTerminalExpressionAccess().getRule();
	}
	
	//HyNestedExpression expr::HyNestedExpression:
	//	'(' operand=HyExpression ')';
	public ExpressionDslGrammarAccess.HyNestedExpressionElements getHyNestedExpressionAccess() {
		return gaExpressionDsl.getHyNestedExpressionAccess();
	}
	
	public ParserRule getHyNestedExpressionRule() {
		return getHyNestedExpressionAccess().getRule();
	}
	
	//HyNotExpression expr::HyNotExpression:
	//	'!' operand=TerminalExpression;
	public ExpressionDslGrammarAccess.HyNotExpressionElements getHyNotExpressionAccess() {
		return gaExpressionDsl.getHyNotExpressionAccess();
	}
	
	public ParserRule getHyNotExpressionRule() {
		return getHyNotExpressionAccess().getRule();
	}
	
	//HyFeatureReferenceExpression expr::HyFeatureReferenceExpression:
	//	feature=[feature::HyFeature|STRING] versionRestriction=HyVersionRestriction?;
	public ExpressionDslGrammarAccess.HyFeatureReferenceExpressionElements getHyFeatureReferenceExpressionAccess() {
		return gaExpressionDsl.getHyFeatureReferenceExpressionAccess();
	}
	
	public ParserRule getHyFeatureReferenceExpressionRule() {
		return getHyFeatureReferenceExpressionAccess().getRule();
	}
	
	//HyConditionalFeatureReferenceExpression expr::HyConditionalFeatureReferenceExpression:
	//	"?" feature=[feature::HyFeature|STRING] versionRestriction=HyVersionRestriction?;
	public ExpressionDslGrammarAccess.HyConditionalFeatureReferenceExpressionElements getHyConditionalFeatureReferenceExpressionAccess() {
		return gaExpressionDsl.getHyConditionalFeatureReferenceExpressionAccess();
	}
	
	public ParserRule getHyConditionalFeatureReferenceExpressionRule() {
		return getHyConditionalFeatureReferenceExpressionAccess().getRule();
	}
	
	//HyVersionRestriction expr::HyVersionRestriction:
	//	HyRelativeVersionRestriction | HyVersionRangeRestriction;
	public ExpressionDslGrammarAccess.HyVersionRestrictionElements getHyVersionRestrictionAccess() {
		return gaExpressionDsl.getHyVersionRestrictionAccess();
	}
	
	public ParserRule getHyVersionRestrictionRule() {
		return getHyVersionRestrictionAccess().getRule();
	}
	
	//HyRelativeVersionRestriction expr::HyRelativeVersionRestriction:
	//	"[" operator=HyRelativeVersionRestrictionOperator version=[feature::HyVersion] "]";
	public ExpressionDslGrammarAccess.HyRelativeVersionRestrictionElements getHyRelativeVersionRestrictionAccess() {
		return gaExpressionDsl.getHyRelativeVersionRestrictionAccess();
	}
	
	public ParserRule getHyRelativeVersionRestrictionRule() {
		return getHyRelativeVersionRestrictionAccess().getRule();
	}
	
	//enum HyRelativeVersionRestrictionOperator returns expr::HyRelativeVersionRestrictionOperator:
	//	lessThan="<" | lessThanOrEqual="<=" | equal="=" | greaterThanOrEqual=">=" | greaterThan=">";
	public ExpressionDslGrammarAccess.HyRelativeVersionRestrictionOperatorElements getHyRelativeVersionRestrictionOperatorAccess() {
		return gaExpressionDsl.getHyRelativeVersionRestrictionOperatorAccess();
	}
	
	public EnumRule getHyRelativeVersionRestrictionOperatorRule() {
		return getHyRelativeVersionRestrictionOperatorAccess().getRule();
	}
	
	//HyVersionRangeRestriction expr::HyVersionRangeRestriction:
	//	"[" lowerIncluded?='true' | 'false' lowerVersion=[feature::HyVersion|STRING] "-" upperIncluded?='true' | 'false'
	//	upperVersion=[feature::HyVersion|STRING] "]";
	public ExpressionDslGrammarAccess.HyVersionRangeRestrictionElements getHyVersionRangeRestrictionAccess() {
		return gaExpressionDsl.getHyVersionRangeRestrictionAccess();
	}
	
	public ParserRule getHyVersionRangeRestrictionRule() {
		return getHyVersionRangeRestrictionAccess().getRule();
	}
	
	//HyBooleanValueExpression expr::HyBooleanValueExpression:
	//	{expr::HyBooleanValueExpression} (value?='true' | 'false');
	public ExpressionDslGrammarAccess.HyBooleanValueExpressionElements getHyBooleanValueExpressionAccess() {
		return gaExpressionDsl.getHyBooleanValueExpressionAccess();
	}
	
	public ParserRule getHyBooleanValueExpressionRule() {
		return getHyBooleanValueExpressionAccess().getRule();
	}
	
	//HyArithmeticalComparisonExpression expr::HyArithmeticalComparisonExpression:
	//	HyBinaryArithmeticalComparisonExpression;
	public ExpressionDslGrammarAccess.HyArithmeticalComparisonExpressionElements getHyArithmeticalComparisonExpressionAccess() {
		return gaExpressionDsl.getHyArithmeticalComparisonExpressionAccess();
	}
	
	public ParserRule getHyArithmeticalComparisonExpressionRule() {
		return getHyArithmeticalComparisonExpressionAccess().getRule();
	}
	
	//HyBinaryArithmeticalComparisonExpression expr::HyBinaryArithmeticalComparisonExpression:
	//	HyGreaterOrEqualExpression | HyGreaterExpression | HyLessOrEqualExpression | HyLessExpression | HyNotEqualExpression
	//	| HyEqualExpression;
	public ExpressionDslGrammarAccess.HyBinaryArithmeticalComparisonExpressionElements getHyBinaryArithmeticalComparisonExpressionAccess() {
		return gaExpressionDsl.getHyBinaryArithmeticalComparisonExpressionAccess();
	}
	
	public ParserRule getHyBinaryArithmeticalComparisonExpressionRule() {
		return getHyBinaryArithmeticalComparisonExpressionAccess().getRule();
	}
	
	//HyGreaterExpression expr::HyGreaterExpression:
	//	operand1=HyArithmeticalValueExpression '>' operand2=HyArithmeticalValueExpression;
	public ExpressionDslGrammarAccess.HyGreaterExpressionElements getHyGreaterExpressionAccess() {
		return gaExpressionDsl.getHyGreaterExpressionAccess();
	}
	
	public ParserRule getHyGreaterExpressionRule() {
		return getHyGreaterExpressionAccess().getRule();
	}
	
	//HyLessExpression expr::HyLessExpression:
	//	operand1=HyArithmeticalValueExpression '<' operand2=HyArithmeticalValueExpression;
	public ExpressionDslGrammarAccess.HyLessExpressionElements getHyLessExpressionAccess() {
		return gaExpressionDsl.getHyLessExpressionAccess();
	}
	
	public ParserRule getHyLessExpressionRule() {
		return getHyLessExpressionAccess().getRule();
	}
	
	//HyGreaterOrEqualExpression expr::HyGreaterOrEqualExpression:
	//	operand1=HyArithmeticalValueExpression '>=' operand2=HyArithmeticalValueExpression;
	public ExpressionDslGrammarAccess.HyGreaterOrEqualExpressionElements getHyGreaterOrEqualExpressionAccess() {
		return gaExpressionDsl.getHyGreaterOrEqualExpressionAccess();
	}
	
	public ParserRule getHyGreaterOrEqualExpressionRule() {
		return getHyGreaterOrEqualExpressionAccess().getRule();
	}
	
	//HyLessOrEqualExpression expr::HyLessOrEqualExpression:
	//	operand1=HyArithmeticalValueExpression '<=' operand2=HyArithmeticalValueExpression;
	public ExpressionDslGrammarAccess.HyLessOrEqualExpressionElements getHyLessOrEqualExpressionAccess() {
		return gaExpressionDsl.getHyLessOrEqualExpressionAccess();
	}
	
	public ParserRule getHyLessOrEqualExpressionRule() {
		return getHyLessOrEqualExpressionAccess().getRule();
	}
	
	//HyEqualExpression expr::HyEqualExpression:
	//	operand1=HyArithmeticalValueExpression '=' operand2=HyArithmeticalValueExpression;
	public ExpressionDslGrammarAccess.HyEqualExpressionElements getHyEqualExpressionAccess() {
		return gaExpressionDsl.getHyEqualExpressionAccess();
	}
	
	public ParserRule getHyEqualExpressionRule() {
		return getHyEqualExpressionAccess().getRule();
	}
	
	//HyNotEqualExpression expr::HyNotEqualExpression:
	//	operand1=HyArithmeticalValueExpression '!=' operand2=HyArithmeticalValueExpression;
	public ExpressionDslGrammarAccess.HyNotEqualExpressionElements getHyNotEqualExpressionAccess() {
		return gaExpressionDsl.getHyNotEqualExpressionAccess();
	}
	
	public ParserRule getHyNotEqualExpressionRule() {
		return getHyNotEqualExpressionAccess().getRule();
	}
	
	//HyArithmeticalValueExpression expr::HyArithmeticalValueExpression:
	//	HyDivisionExpression;
	public ExpressionDslGrammarAccess.HyArithmeticalValueExpressionElements getHyArithmeticalValueExpressionAccess() {
		return gaExpressionDsl.getHyArithmeticalValueExpressionAccess();
	}
	
	public ParserRule getHyArithmeticalValueExpressionRule() {
		return getHyArithmeticalValueExpressionAccess().getRule();
	}
	
	//HyDivisionExpression expr::HyArithmeticalValueExpression:
	//	HyMultiplicationExpression ({expr::HyDivisionExpression.operand1=current} '/' operand2=HyMultiplicationExpression)*;
	public ExpressionDslGrammarAccess.HyDivisionExpressionElements getHyDivisionExpressionAccess() {
		return gaExpressionDsl.getHyDivisionExpressionAccess();
	}
	
	public ParserRule getHyDivisionExpressionRule() {
		return getHyDivisionExpressionAccess().getRule();
	}
	
	//HyMultiplicationExpression expr::HyArithmeticalValueExpression:
	//	HySubtractionExpression ({expr::HyMultiplicationExpression.operand1=current} '*' operand2=HySubtractionExpression)*;
	public ExpressionDslGrammarAccess.HyMultiplicationExpressionElements getHyMultiplicationExpressionAccess() {
		return gaExpressionDsl.getHyMultiplicationExpressionAccess();
	}
	
	public ParserRule getHyMultiplicationExpressionRule() {
		return getHyMultiplicationExpressionAccess().getRule();
	}
	
	//HySubtractionExpression expr::HyArithmeticalValueExpression:
	//	HyAdditionExpression ({expr::HySubtractionExpression.operand1=current} '-' operand2=HyAdditionExpression)*;
	public ExpressionDslGrammarAccess.HySubtractionExpressionElements getHySubtractionExpressionAccess() {
		return gaExpressionDsl.getHySubtractionExpressionAccess();
	}
	
	public ParserRule getHySubtractionExpressionRule() {
		return getHySubtractionExpressionAccess().getRule();
	}
	
	//HyAdditionExpression expr::HyArithmeticalValueExpression:
	//	TerminalArithmeticalExpression ({expr::HyAdditionExpression.operand1=current} '+'
	//	operand2=TerminalArithmeticalExpression)*;
	public ExpressionDslGrammarAccess.HyAdditionExpressionElements getHyAdditionExpressionAccess() {
		return gaExpressionDsl.getHyAdditionExpressionAccess();
	}
	
	public ParserRule getHyAdditionExpressionRule() {
		return getHyAdditionExpressionAccess().getRule();
	}
	
	//TerminalArithmeticalExpression expr::HyArithmeticalValueExpression:
	//	HyValueExpression
	//	| HyNestedArithmeticalValueExpression // TODO here is the problem! I don't know why :( I set backtracking to true in the mwe2 file. Supposed to have disadvantages: less performance, ANTLR reports no warnings about unreachable alternatives, “magic”, etc
	//	//	/*| HyNegationExpression*/
	//	| => HyContextInformationReferenceExpression | HyAttributeReferenceExpression;
	public ExpressionDslGrammarAccess.TerminalArithmeticalExpressionElements getTerminalArithmeticalExpressionAccess() {
		return gaExpressionDsl.getTerminalArithmeticalExpressionAccess();
	}
	
	public ParserRule getTerminalArithmeticalExpressionRule() {
		return getTerminalArithmeticalExpressionAccess().getRule();
	}
	
	//HyValueExpression expr::HyValueExpression:
	//	value=HyValue;
	public ExpressionDslGrammarAccess.HyValueExpressionElements getHyValueExpressionAccess() {
		return gaExpressionDsl.getHyValueExpressionAccess();
	}
	
	public ParserRule getHyValueExpressionRule() {
		return getHyValueExpressionAccess().getRule();
	}
	
	//HyValue dataValues::HyValue:
	//	HyNumberValue | HyStringValue | HyBooleanValue | HyEnumValue;
	public ExpressionDslGrammarAccess.HyValueElements getHyValueAccess() {
		return gaExpressionDsl.getHyValueAccess();
	}
	
	public ParserRule getHyValueRule() {
		return getHyValueAccess().getRule();
	}
	
	//HyNumberValue dataValues::HyNumberValue:
	//	value=EInt;
	public ExpressionDslGrammarAccess.HyNumberValueElements getHyNumberValueAccess() {
		return gaExpressionDsl.getHyNumberValueAccess();
	}
	
	public ParserRule getHyNumberValueRule() {
		return getHyNumberValueAccess().getRule();
	}
	
	//HyStringValue dataValues::HyStringValue:
	//	value=STRING;
	public ExpressionDslGrammarAccess.HyStringValueElements getHyStringValueAccess() {
		return gaExpressionDsl.getHyStringValueAccess();
	}
	
	public ParserRule getHyStringValueRule() {
		return getHyStringValueAccess().getRule();
	}
	
	//HyBooleanValue dataValues::HyBooleanValue:
	//	{dataValues::HyBooleanValue} (value?='true' | 'false');
	public ExpressionDslGrammarAccess.HyBooleanValueElements getHyBooleanValueAccess() {
		return gaExpressionDsl.getHyBooleanValueAccess();
	}
	
	public ParserRule getHyBooleanValueRule() {
		return getHyBooleanValueAccess().getRule();
	}
	
	//HyEnumValue dataValues::HyEnumValue:
	//	^enum=[dataValues::HyEnum|STRING] '.' enumLiteral=[dataValues::HyEnumLiteral|STRING];
	public ExpressionDslGrammarAccess.HyEnumValueElements getHyEnumValueAccess() {
		return gaExpressionDsl.getHyEnumValueAccess();
	}
	
	public ParserRule getHyEnumValueRule() {
		return getHyEnumValueAccess().getRule();
	}
	
	//HyNestedArithmeticalValueExpression expr::HyNestedArithmeticalValueExpression:
	//	'(' operand=HyDivisionExpression ')';
	public ExpressionDslGrammarAccess.HyNestedArithmeticalValueExpressionElements getHyNestedArithmeticalValueExpressionAccess() {
		return gaExpressionDsl.getHyNestedArithmeticalValueExpressionAccess();
	}
	
	public ParserRule getHyNestedArithmeticalValueExpressionRule() {
		return getHyNestedArithmeticalValueExpressionAccess().getRule();
	}
	
	//HyContextInformationReferenceExpression expr::HyContextInformationReferenceExpression:
	//	contextInformation=[context::HyContextualInformation|STRING];
	public ExpressionDslGrammarAccess.HyContextInformationReferenceExpressionElements getHyContextInformationReferenceExpressionAccess() {
		return gaExpressionDsl.getHyContextInformationReferenceExpressionAccess();
	}
	
	public ParserRule getHyContextInformationReferenceExpressionRule() {
		return getHyContextInformationReferenceExpressionAccess().getRule();
	}
	
	//HyAttributeReferenceExpression expr::HyAttributeReferenceExpression:
	//	feature=[feature::HyFeature|STRING] '.' attribute=[feature::HyFeatureAttribute|STRING];
	public ExpressionDslGrammarAccess.HyAttributeReferenceExpressionElements getHyAttributeReferenceExpressionAccess() {
		return gaExpressionDsl.getHyAttributeReferenceExpressionAccess();
	}
	
	public ParserRule getHyAttributeReferenceExpressionRule() {
		return getHyAttributeReferenceExpressionAccess().getRule();
	}
	
	//// Not working right now. Necessary?
	////HyNegationExpression returns HyNegationExpression:
	////	'-' operand=HyArithmeticalValueExpression;
	////QualifiedName returns ecore::EString:
	////ID ("." ID)*;
	//EInt ecore::EInt:
	//	'-'? INT;
	public ExpressionDslGrammarAccess.EIntElements getEIntAccess() {
		return gaExpressionDsl.getEIntAccess();
	}
	
	public ParserRule getEIntRule() {
		return getEIntAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' | "'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
