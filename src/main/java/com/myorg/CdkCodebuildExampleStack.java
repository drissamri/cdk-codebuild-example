package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.codebuild.*;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;

import java.util.HashMap;
import java.util.Map;

import static software.amazon.awscdk.services.iam.ManagedPolicy.fromAwsManagedPolicyName;

public class CdkCodebuildExampleStack extends Stack {
    public CdkCodebuildExampleStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkCodebuildExampleStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);



        BuildEnvironment environment = BuildEnvironment.builder()
                .buildImage(LinuxBuildImage.AMAZON_LINUX_2_2)
                .computeType(ComputeType.SMALL)
                .build();

        Role role = Role.Builder.create(this, "codebuildRole")
                .description("The permissions needed to run codebuild")
                .assumedBy(new ServicePrincipal("codebuild.amazonaws.com"))
                .build();
        role.addManagedPolicy(fromAwsManagedPolicyName("AmazonEC2ContainerRegistryPowerUser"));

        Project.Builder.create(this, "CodebuildCDK")
                .projectName("cdk-codebuild")
                .description("Sample Project using AWS CDK")
                .role(role)
                .buildSpec(BuildSpec.fromSourceFilename("buildspec.yml"))
                .environment(environment)
                .timeout(Duration.minutes(10))
                .build();
    }
}
