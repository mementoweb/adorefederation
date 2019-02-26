#OpenURL resolver grammar
[when]ContextObject=obj:ContextObjectContainer()
[when]srvtypes "{prop}" "{propvalue}"=eval(((obj.getServiceType().getProperty("{prop}")).getString()).equals({propvalue}))
[when]referent "{prop}" "{propvalue}"=eval(((obj.getReferent()).getProperty("{prop}")).equals({propvalue}))
[when]referent has "{prop}"=eval((obj.getReferent()).hasProperty("{prop}"))
[when]requester has "{prop}"=eval(obj.getRequester().hasProperty("{prop}"))
[when]requester hasnot "{prop}"=eval(!obj.getRequester().hasProperty("{prop}"))
[when]requester "{prop}" "{propvalue}"=eval(((obj.getRequester().getProperty("{prop}")).getString()).equals("{propvalue}"))
[when]referent isDataStream "{b}"=eval(((obj.getReferent()).getProperty("isDataStream")).equals("{b}"))
[when]referent "{p}" startwith "{value}"=eval(((obj.getReferent()).getProperty("{p}")).indexOf("{value}")!=-1)
[then]add service "{service}"=(obj.getReferent()).addService({service});
[then]add param "{param}" to service "{service}"=(obj.getReferent()).addParamToService({service},"{param}");