package org.example.cloud.predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * userType in here means the level of user
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    private static final Log log = LogFactory.getLog(MyRoutePredicateFactory.class);
    private static final String MATCH_TRAILING_SLASH = "userType";

    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("userType", MATCH_TRAILING_SLASH);
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.GATHER_LIST_TAIL_FLAG;
    }


    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return serverWebExchange -> {
            System.out.println("222222222222222222");
            String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
            if (userType == null) {
                log.error("userType is null!!!");
                return false;
            } else if (userType.equalsIgnoreCase("diamond")) {
                return true;
            } else {
                log.error("userType is not right , only diamond is allowed ");
                return false;
            }
        };
    }

    @Validated
    public static class Config {

        private String userType;

        private boolean matchTrailingSlash = true;

        public String getUserType() {
            return userType;
        }

        public MyRoutePredicateFactory.Config setUserType(String userType) {
            this.userType = userType;
            return this;
        }

        /**
         * @deprecated use {@link #isMatchTrailingSlash()}
         */
        @Deprecated
        public boolean isMatchOptionalTrailingSeparator() {
            return isMatchTrailingSlash();
        }

        /**
         * @deprecated use {@link #setMatchTrailingSlash(boolean)}
         */
        @Deprecated
        public MyRoutePredicateFactory.Config setMatchOptionalTrailingSeparator(boolean matchOptionalTrailingSeparator) {
            setMatchTrailingSlash(matchOptionalTrailingSeparator);
            return this;
        }

        public boolean isMatchTrailingSlash() {
            return matchTrailingSlash;
        }

        public MyRoutePredicateFactory.Config setMatchTrailingSlash(boolean matchTrailingSlash) {
            this.matchTrailingSlash = matchTrailingSlash;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringCreator(this).append("userType", userType)
                    .append(MATCH_TRAILING_SLASH, matchTrailingSlash).toString();
        }
    }
}
