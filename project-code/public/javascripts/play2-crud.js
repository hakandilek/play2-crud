$(document).ready(function(){
    window.choices = [];
    window.onChoose = function(id, value, data) {
        window.choices[id](value, data);
    }
    var registerAChoice = function(relation, relationId, fieldId, fieldName, isMultiple) {
        var inputs = $("#"+relation);
        var defaultInput = "#"+relation+"DefaultValue";
        var update = function() {
            if(inputs.find("input[name^='"+fieldName+"']:not("+defaultInput+")").size() == 0) {
                inputs.find(defaultInput).attr("disabled", false);
                inputs.next().show();
            }else{
                inputs.find("#"+relation+"DefaultValue").attr("disabled", true);
                if(!isMultiple)
                    inputs.next().hide();
            }
        }
        choices[relation]=function(value, data) {
            if(inputs.find("input[value='"+value+"']").size() == 0) {
                var nextId =fieldId;
                var nextName = fieldName;
                if(isMultiple) {
                    var nextIndex = inputs.find("input[name^='"+fieldName+"']:not("+defaultInput+")").size();
                    nextId += "_"+nextIndex+"_";
                    nextName += "["+nextIndex+"]";
                }
                inputs.append("<div><input type='hidden' id='"+nextId+"' name='"+nextName+"."+relationId+"' value='"+value+"'><a href='#' rel='remove' title='Remove'><i class='icon-remove'></i></a> <span>"+data+"</span></div>");
                update();
            }
        };
        inputs.find("a[rel='remove']").live("click", function(e){
            e.preventDefault();
            var inputHolder = $(this).parent();
            var index = inputHolder.index() + 1;
            var inputHolders = inputHolder.parent().children();
            while(index < inputHolders.size()) {
                var i = inputHolders.eq(index).find("input[name^='"+fieldName+"']");
                i.attr("name", fieldName+"["+(index-1)+"]."+relationId);
                i.attr("id", fieldName+"_"+(index+-1)+"_");
                index++;
            }
            inputHolder.remove();
            update();
        });
        update();
    }
    $(".relation-datas").each(function() {
        var inputs = $(this);
        registerAChoice(inputs.attr("id"), inputs.data("relationId"), inputs.data("fieldId"), inputs.data("fieldName"), inputs.data("isMultiple"));
    });

});